package com.libktx.game.puzzle

import com.libktx.game.ecs.network.PuzzleResponse
import com.libktx.game.ecs.network.ResponseStatus

/**
 * The player must provide the string `goin`
 */
class LoginPuzzle : AbstractPuzzleEndpoint(Puzzle.Login) {

    override fun request(data: String): PuzzleResponse {
        return if ("goin" == data.toLowerCase()) {
            PuzzleResponse(status = ResponseStatus.OK)
        } else {
            PuzzleResponse(status = ResponseStatus.FALSE)
        }
    }

}