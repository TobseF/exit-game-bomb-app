package com.libktx.game.ecs.network

interface NetworkEventListener{
    fun receivedNetworkEvent(networkEvent: NetworkEvent)
}