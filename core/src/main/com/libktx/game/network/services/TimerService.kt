package com.libktx.game.network.services

import com.libktx.game.Config
import com.libktx.game.Preferences
import com.libktx.game.network.NetworkEvent.EventType
import com.libktx.game.network.NetworkEvent.EventType.GET
import kotlinx.coroutines.launch
import ktx.async.KtxAsync
import ktx.async.httpRequest
import ktx.async.newSingleThreadAsyncContext
import ktx.log.logger

private val log = logger<TimerService>()

/**
 * Access the external 7-segment timer.
 */
class TimerService {

    private val executor = newSingleThreadAsyncContext()

    private fun getPath(endpoint: String, time: Long) = getPath(endpoint) + "?time=$time"

    private fun getPath(endpoint: String) = "http://${Preferences.timerIp}:${Config.timerPort}/$endpoint"

    /**
     * Start's the timer
     * @param time in milliseconds
     */
    fun start(time: Long) {
        val path = getPath("start", time)
        log.info { "Start external timer with $time ms on: $path" }
        asyncHttpRequest(path)
    }

    /**
     * Stops the timer and displays the finish time
     */
    fun stop(time: Long) {
        val path = getPath("stop", time)
        log.info { "Stop external timer with $time ms on: $path" }
        asyncHttpRequest(path)
    }

    /**
     * Deactivates the timer, so all lights are off.
     */
    fun disable() {
        val path = getPath("disable")
        log.info { "Stop external timer on: $path" }
        asyncHttpRequest(path)
    }

    private fun asyncHttpRequest(url: String, type: EventType = GET, content: String? = null) {
        KtxAsync.launch(executor) {
            try {
                val response = httpRequest(url = url, content = content, method = type.toString())
                if (response.statusCode != 200) {
                    log.error { "Failed access timer on url (code: ${response.statusCode}): $url" }
                }
            } catch (e: Exception) {
                log.error(e) { "Failed access timer on url: $url" }
            }
        }
    }

}