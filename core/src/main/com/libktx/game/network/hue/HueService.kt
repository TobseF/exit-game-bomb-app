package com.libktx.game.network.hue

import com.badlogic.gdx.graphics.Color
import com.libktx.game.Config
import com.libktx.game.Preferences
import io.github.zeroone3010.yahueapi.Hue
import io.github.zeroone3010.yahueapi.HueApiException
import io.github.zeroone3010.yahueapi.Room
import io.github.zeroone3010.yahueapi.State
import ktx.log.logger
import java.util.concurrent.ExecutionException

typealias HueColor = common.util.Color


private val log = logger<HueService>()

class HueService {
    private var hue: Hue? = null

    enum class LightState { ON, OFF }

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


    fun setLights(color: Color, lightState: LightState) {
        hue?.let { hue ->
            val roomName = Preferences.hueRoomName ?: "Bomb"
            val room = hue.getRoomByName(roomName)!!
            setLights(color, room, lightState)
        } ?: run {
            log.error { "Hue is not connected. Setting light $lightState to $color was not possible." }
        }
    }

    private fun setLights(color: Color, room: Room, lightState: LightState) {
        val hueState = State.builder().color(color.toJavaColor()).let {
            when (lightState) {
                LightState.ON -> it.on()
                LightState.OFF -> it.off()
            }
        }
        log.error { "Setting light $lightState to $color." }
        room.setState(hueState)
    }


    private fun Color.toJavaColor() = HueColor(this.r, this.g, this.b, this.a)

}