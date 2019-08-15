package com.libktx.game.puzzle

import com.libktx.game.network.PuzzleResponse

/**
 * The player must provide the string `goin`
 */
class LoginPuzzle : AbstractPuzzleEndpoint(Puzzle.Connect) {

    override fun request(data: String): PuzzleResponse {
        return if ("goin" == data.toLowerCase()) {
            PuzzleResponse.OK
        } else {
            PuzzleResponse.FALSE
        }
    }

}