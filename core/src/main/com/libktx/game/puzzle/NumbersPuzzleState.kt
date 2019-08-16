package com.libktx.game.puzzle

import kotlin.random.Random

class NumbersPuzzleState(var numbers: List<Int> = generateRandomNumbers()) {

    companion object {

        private const val numbersCount = 3 * 6

        private fun generateRandomNumbers(): ArrayList<Int> {
            val numbers = arrayListOf<Int>()
            for (i in 0 until numbersCount) {
                numbers.add(nextRandomNumber())
            }
            return numbers
        }

        private fun nextRandomNumber() = Random.nextInt(1000, 9042)
    }

    fun calculateNewNumbers() {
        numbers = generateRandomNumbers()
    }

    fun numbersAsString(): String {
        return numbers.joinToString("") { it.asFourDigits() }
    }

    private fun Int.asFourDigits() = String.format("%04d", this)

}