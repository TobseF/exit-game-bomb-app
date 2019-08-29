package com.libktx.game.network

import com.libktx.game.network.NetworkEvent.EventType
import com.libktx.game.puzzle.PuzzleResponseHandler
import com.libktx.game.screen.AbstractPuzzleScreen
import com.libktx.game.screen.BombState
import ktx.log.logger

private val log = logger<NetworkEventManager>()

class NetworkEventManager(private val bombState: BombState) : NetworkEventListener {

    private val endpoints: MutableMap<Endpoint, NetworkEndpoint> = mutableMapOf()
    private val puzzlesScreens: MutableMap<Endpoint, PuzzleResponseHandler> = mutableMapOf()

    override fun receivedNetworkEvent(event: NetworkEvent): PuzzleResponse {
        val endpoint = Endpoint[event.endpoint]
        log.info { "Received network event: $event" }
        if (endpoint != null) {
            if (bombState.isPuzzle(endpoint) && bombState.currentPuzzle != endpoint) {
                log.error { "Trying to access inactive puzzle: $endpoint" }
                return PuzzleResponse.FALSE
            }
            val networkEndpoint = endpoints[endpoint]

            return if (networkEndpoint != null) {
                accessNetworkEndpoint(event, networkEndpoint, endpoint)
            } else {
                log.error { "Failed finding networkEndpoint for endpoint path: ${event.endpoint}" }
                PuzzleResponse.FALSE
            }
        } else {
            log.error { "Failed finding Endpoint for path: ${event.endpoint}" }
            return PuzzleResponse.FALSE
        }
    }

    private fun accessNetworkEndpoint(event: NetworkEvent, networkEndpoint: NetworkEndpoint, endpoint: Endpoint): PuzzleResponse {
        return when (event.eventType) {
            EventType.POST -> {
                val response: PuzzleResponse = networkEndpoint.request(event.data)
                puzzlesScreens[endpoint]?.handlePuzzleResponse(response)
                response
            }
            EventType.GET -> {
                PuzzleResponse(networkEndpoint.getPuzzleData(), ResponseStatus.OK)
            }
        }
    }

    fun addEndpoint(endpoint: AbstractNetworkEndpoint) {
        endpoints[endpoint.name] = endpoint
    }

    fun addPuzzleScreen(puzzle: AbstractPuzzleScreen) {
        puzzlesScreens[puzzle.endpoint] = puzzle
    }

}