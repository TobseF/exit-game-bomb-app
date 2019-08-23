package com.libktx.game.network

interface NetworkEndpoint : NetworkDataProvider, NetworkEventListener {
    val name: Endpoint

    fun request(data: String): PuzzleResponse
}