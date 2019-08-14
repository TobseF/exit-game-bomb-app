package com.libktx.game.ecs.network

import java.util.*

data class NetworkEvent(private val endpointPath: String, val eventType: EventType, val data: String = "", val time: Date = Date(), val counter: Long = count()) {

    val endpoint: String = endpointPath.removePrefix("/").trim()

    enum class EventType { POST, GET }

    companion object {
        var counter = 0L
        fun count() = counter++
    }

}