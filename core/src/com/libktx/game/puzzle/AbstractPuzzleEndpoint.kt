package com.libktx.game.puzzle

import com.libktx.game.ecs.network.PuzzleResponse

abstract class AbstractPuzzleEndpoint(val puzze: Puzzle) {

    abstract fun request(data: String): PuzzleResponse


}