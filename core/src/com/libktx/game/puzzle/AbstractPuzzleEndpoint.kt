package com.libktx.game.puzzle

import com.libktx.game.ecs.network.NetworkDataProvider
import com.libktx.game.ecs.network.PuzzleResponse

abstract class AbstractPuzzleEndpoint(val puzzle: Puzzle) : NetworkDataProvider {

    abstract fun request(data: String): PuzzleResponse

    override fun getPuzzleData() = ""
}