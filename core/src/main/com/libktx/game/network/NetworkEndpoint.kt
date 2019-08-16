package com.libktx.game.network

interface NetworkEndpoint : NetworkDataProvider, NetworkEventListener {
    val name: String
}