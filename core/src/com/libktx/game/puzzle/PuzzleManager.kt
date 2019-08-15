package com.libktx.game.puzzle

import com.libktx.game.network.NetworkEvent
import com.libktx.game.network.NetworkEvent.EventType
import com.libktx.game.network.NetworkEventListener
import com.libktx.game.network.PuzzleResponse
import com.libktx.game.network.ResponseStatus
import com.libktx.game.screen.AbstractPuzzleScreen
import ktx.log.logger

private val log = logger<PuzzleManager>()

class PuzzleManager : NetworkEventListener {

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
                    val response = PuzzleResponse(puzzle.getPuzzleData(), ResponseStatus.OK)
                    return response
                }
            }
        } else {
            log.info { "Failed finding puzzle for endpoint path: ${event.endpoint}" }
            return PuzzleResponse.FALSE
        }
    }

    private val puzzles: MutableMap<String, AbstractPuzzleEndpoint> = mutableMapOf()
    private val puzzlesScreens: MutableMap<String, AbstractPuzzleScreen> = mutableMapOf()


    fun addPuzzle(puzzle: AbstractPuzzleEndpoint) {
        puzzles[puzzle.puzzle.endpoint] = puzzle
    }

    fun addPuzzleScreen(puzzle: AbstractPuzzleScreen) {
        puzzlesScreens[puzzle.puzzle.endpoint] = puzzle
    }

}