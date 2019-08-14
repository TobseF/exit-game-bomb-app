package com.libktx.game.puzzle

import com.libktx.game.ecs.network.PuzzleResponse
import com.libktx.game.ecs.network.ResponseStatus

/**
 * The player must provide a string with all current numbers, sorted in the correct way. Each number has 4 digits, they are sorted ascending.
 */
class NumbersPuzzle(val state: NumbersPuzzleState) : AbstractPuzzleEndpoint(Puzzle.Numbers) {

    override fun request(data: String): PuzzleResponse {
        return if (isValidNumber(data)) {
            PuzzleResponse(status = ResponseStatus.OK)
        } else {
            PuzzleResponse(status = ResponseStatus.FALSE)
        }
    }

    fun isValidNumber(data: String): Boolean {
        return data == getSortedNumbers()
    }

    fun getSortedNumbers() = NumbersPuzzleState(state.numbers.sorted()).numbersAsString()

}