package com.libktx.game.puzzle

import com.libktx.game.network.PuzzleResponse

interface PuzzleResponseHandler {
    fun handlePuzzleResponse(response: PuzzleResponse)
}