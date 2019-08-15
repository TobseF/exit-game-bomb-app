package com.libktx.game.network

interface NetworkEventListener {
    fun receivedNetworkEvent(event: NetworkEvent): PuzzleResponse
}