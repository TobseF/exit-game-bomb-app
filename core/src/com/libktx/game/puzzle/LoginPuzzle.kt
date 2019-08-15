package com.libktx.game.puzzle

import com.libktx.game.network.PuzzleResponse
import com.libktx.game.network.ResponseStatus

/**
 * The player must provide the string `goin`
 */
class LoginPuzzle : AbstractPuzzleEndpoint(Puzzle.Connect) {

    override fun request(data: String): PuzzleResponse {
        return if ("goin" == data.toLowerCase()) {
            PuzzleResponse(status = ResponseStatus.OK)
        } else {
            PuzzleResponse(status = ResponseStatus.FALSE)
        }
    }

}