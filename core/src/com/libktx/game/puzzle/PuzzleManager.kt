package com.libktx.game.puzzle

import com.libktx.game.ecs.network.NetworkEvent
import com.libktx.game.ecs.network.NetworkEventListener
import com.libktx.game.ecs.network.PuzzleResponse
import com.libktx.game.screen.AbstractPuzzleScreen
import ktx.log.logger

private val log = logger<PuzzleManager>()

class PuzzleManager : NetworkEventListener {

    override fun receivedNetworkEvent(event: NetworkEvent): PuzzleResponse {
        val puzzle = puzzles[event.endpoint]
        return if (puzzle != null) {
            val response: PuzzleResponse = puzzle.request(event.data)
            puzzlesScreens[event.endpoint]?.handlePuzzleResponse(response)
            response
        } else {
            log.info { "Failed finding puzzle for endpoint path: ${event.endpoint}" }
            PuzzleResponse.FALSE
        }
    }

    private val puzzles: MutableMap<String, AbstractPuzzleEndpoint> = mutableMapOf()
    private val puzzlesScreens: MutableMap<String, AbstractPuzzleScreen> = mutableMapOf()


    fun addPuzzle(puzzle: AbstractPuzzleEndpoint) {
        puzzles[puzzle.puzze.endpoint] = puzzle
    }

    fun addPuzzleScreen(puzzle: AbstractPuzzleScreen) {
        puzzlesScreens[puzzle.puzzle.endpoint] = puzzle
    }

}