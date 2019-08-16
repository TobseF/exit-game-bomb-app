package com.libktx.game.network

import com.libktx.game.network.NetworkEvent.EventType
import com.libktx.game.puzzle.PuzzleResponseHandler
import com.libktx.game.screen.AbstractPuzzleScreen
import ktx.log.logger

private val log = logger<NetworkEventManager>()

class NetworkEventManager : NetworkEventListener {

    private val puzzles: MutableMap<String, AbstractNetworkEndpoint> = mutableMapOf()
    private val puzzlesScreens: MutableMap<String, PuzzleResponseHandler> = mutableMapOf()

    override fun receivedNetworkEvent(event: NetworkEvent): PuzzleResponse {
        val puzzle = puzzles[event.endpoint]
        if (puzzle != null) {

            when (event.eventType) {
                EventType.POST -> {
                    val response: PuzzleResponse = puzzle.request(event.data)
                    puzzlesScreens[event.endpoint]?.handlePuzzleResponse(response)
                    return response
                }
                EventType.GET -> {
                    return PuzzleResponse(puzzle.getPuzzleData(), ResponseStatus.OK)
                }
            }
        } else {
            log.info { "Failed finding puzzle for endpoint path: ${event.endpoint}" }
            return PuzzleResponse.FALSE
        }
    }

    fun addPuzzle(endpoint: AbstractNetworkEndpoint) {
        puzzles[endpoint.name] = endpoint
    }

    fun addPuzzleScreen(puzzle: AbstractPuzzleScreen) {
        puzzlesScreens[puzzle.endpoint.path] = puzzle
    }

}