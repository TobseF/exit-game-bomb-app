package com.libktx.game.ecs.network

import java.util.*

data class NetworkEvent(private val endpointPath: String, val data: String, val time: Date = Date(), val counter: Long = count()) {

    val endpoint: String = endpointPath.removePrefix("/").trim()

    companion object {
        var counter = 0L
        fun count() = counter++
    }


}