package com.libktx.game.puzzle

import com.libktx.game.network.AbstractNetworkEndpoint
import com.libktx.game.network.Endpoint
import com.libktx.game.network.PuzzleResponse
import com.libktx.game.network.ResponseStatus

/**
 * The player must provide a string with all current numbers, sorted in the correct way. Each number has 4 digits, they are sorted ascending.
 */
class NumbersPuzzle(private val state: NumbersPuzzleState) : AbstractNetworkEndpoint(Endpoint.Numbers.path) {

    override fun request(data: String): PuzzleResponse {
        return if (isValidNumber(data)) {
            PuzzleResponse(status = ResponseStatus.OK)
        } else {
            PuzzleResponse(status = ResponseStatus.FALSE)
        }
    }

    private fun isValidNumber(data: String): Boolean {
        return data == getSortedNumbers()
    }

    fun getSortedNumbers() = NumbersPuzzleState(state.numbers.sorted()).numbersAsString()

    override fun getPuzzleData(): String = state.numbersAsString()
}