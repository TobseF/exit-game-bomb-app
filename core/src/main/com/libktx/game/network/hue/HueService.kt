package com.libktx.game.network.hue

import com.libktx.game.Config
import com.libktx.game.Preferences
import io.github.zeroone3010.yahueapi.Hue
import io.github.zeroone3010.yahueapi.HueApiException
import io.github.zeroone3010.yahueapi.Room
import io.github.zeroone3010.yahueapi.State
import ktx.log.logger
import java.util.concurrent.ExecutionException


private val log = logger<HueService>()

class HueService {
    private var hue: Hue? = null

    enum class LightState { ON, OFF }

    private val HUE_MAX = 254

    fun isPaired() = hue != null

    fun pair(): Boolean {
        val ipAddress = Preferences.hueIp

        if (ipAddress == null) {
            log.error { "No ip address for hue bridge specified" }
            return false
        }

        val apiKey = Preferences.hueApiKey
        if (apiKey == null) {
            log.info { "No API key for bridge was present... trying initialize new API connection " }
            val newApiKey = acquireNewApiKey(ipAddress)
            Preferences.hueApiKey = newApiKey
            if (newApiKey == null) {
                return false
            } else {
                hue = Hue(ipAddress, newApiKey)
            }
        } else {
            hue = Hue(ipAddress, apiKey)
        }
        return true
    }

    private fun acquireNewApiKey(ipAddress: String): String? {
        log.info { "Initialize new API connection" }
        val newKey = Hue.hueBridgeConnectionBuilder(ipAddress).initializeApiConnection(Config.appIdentifier)
        log.info { "Push the button on your Hue Bridge to resolve the apiKey future" }
        try {
            return newKey.get()!!
        } catch (e: HueApiException) {
            log.error { "Failed connecting to bridge" }
            return null
        } catch (e: ExecutionException) {
            log.error { "Failed connecting to bridge" }
            return null
        }
    }

    enum class HueColor(val hue: Int) { Red(1), Green(21480) }

    fun setLights(color: HueColor, lightState: LightState) {
        setLights(color.hue, lightState)
    }


    fun setLights(hueValue: Int, lightState: LightState) {
        hue?.let { hue ->
            val roomName = Preferences.hueRoomName ?: "Bomb"
            val roomByName = hue.getRoomByName(roomName)

            if (roomByName == null) {
                val firstRoom = hue.getRooms().firstOrNull()
                if (firstRoom != null) {
                    log.info { "Hue is connected but room '$roomName' is not present. Using first room: '${firstRoom.name}'" }
                    setLights(hueValue, firstRoom, lightState)
                } else {
                    log.error { "Hue is connected but no room is present. Setting light $lightState to $hueValue was not possible." }
                }
            } else {
                setLights(hueValue, roomByName, lightState)
            }
        } ?: run {
            log.error { "Hue is not connected. Setting light $lightState to $hueValue was not possible." }
        }
    }

    private fun setLights(hueValue: Int, room: Room, lightState: LightState) {
        val hueState = State.builder().hue(hueValue).saturation(HUE_MAX).brightness(HUE_MAX).let {
            when (lightState) {
                LightState.ON -> it.on()
                LightState.OFF -> it.off()
            }
        }
        log.info { "Setting light $lightState to $hueValue." }
        room.setState(hueState)
    }

}