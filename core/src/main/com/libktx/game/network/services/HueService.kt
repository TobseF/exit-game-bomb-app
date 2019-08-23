package com.libktx.game.network.services

import com.libktx.game.Config
import com.libktx.game.Preferences
import io.github.zeroone3010.yahueapi.Hue
import io.github.zeroone3010.yahueapi.HueApiException
import io.github.zeroone3010.yahueapi.Room
import io.github.zeroone3010.yahueapi.State
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ktx.async.KtxAsync
import ktx.async.newSingleThreadAsyncContext
import ktx.log.logger
import java.util.concurrent.ExecutionException


private val log = logger<HueService>()

class HueService {
    private var hue: Hue? = null

    private val executor = newSingleThreadAsyncContext()

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
        val connectionBuiler = Hue.hueBridgeConnectionBuilder(ipAddress)
        log.info { "Push the button on your Hue Bridge to resolve the apiKey future" }
        try {
            return connectionBuiler.initializeApiConnection(Config.appIdentifier)
        } catch (e: HueApiException) {
            log.error { "Failed connecting to bridge" }
            return null
        } catch (e: ExecutionException) {
            log.error { "Failed connecting to bridge" }
            return null
        }
    }

    enum class HueValue(val hue: Int) { Red(1), Green(21480), White(32640) }

    fun setLights(color: HueValue, lightState: LightState, brightness: Int = HUE_MAX, transitionTime: Int = 8) {
        setLights(color.hue, lightState, brightness, transitionTime)
    }


    fun setLights(hueValue: Int, lightState: LightState, brightness: Int = HUE_MAX, transitionTime: Int = 8) {
        try {
            hue?.let { _ ->
                val room = findRoom()
                if (room == null) {
                    log.error { "Hue is connected but no room is present. Setting light $lightState to $hueValue was not possible." }
                } else {
                    setLights(hueValue, brightness, transitionTime, room, lightState)
                }
            } ?: run {
                log.error { "Hue is not connected. Setting light $lightState to $hueValue was not possible." }
            }
        } catch (e: HueApiException) {
            log.error { "Hue is not connected. Setting light $lightState to $hueValue was not possible." }
        }
    }

    private fun findRoom(): Room? {
        val roomName = Preferences.hueRoomName ?: "Bomb"
        val roomByName = hue?.getRoomByName(roomName)
        if (roomByName == null) {
            val firstRoom = hue?.getRooms()?.firstOrNull()
            if (firstRoom != null) {
                log.info { "Hue is connected but room '$roomName' is not present. Using first room: '${firstRoom.name}'" }
                return firstRoom
            } else {
                return null
            }
        } else {
            return roomByName
        }
    }

    private fun setLights(hueValue: Int, brightness: Int = HUE_MAX, transitionTime: Int = 8, room: Room, state: LightState) {
        KtxAsync.launch {
            log.info { "Setting light $state to $hueValue." }
            val hueState = State.builder().hue(hueValue).saturation(HUE_MAX).brightness(brightness).transitionTime(transitionTime).let {
                when (state) {
                    LightState.ON -> it.on()
                    LightState.OFF -> it.off()
                }
            }
            withContext(executor) {
                room.setState(hueState)
            }
            log.info { "Finished setting light" }
        }

    }

}