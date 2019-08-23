package com.libktx.game.network

abstract class AbstractNetworkEndpoint(override val name: Endpoint) : NetworkEndpoint {

    abstract override fun request(data: String): PuzzleResponse

    override fun receivedNetworkEvent(event: NetworkEvent) = request(event.data)

    override fun getPuzzleData() = ""
}