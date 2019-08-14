package com.libktx.game.puzzle

import kotlin.random.Random

class NumbersPuzzleState(var numbers: List<Int> = generateRandomNumbers()) {

    fun calculateNewNumbers() {
        numbers = generateRandomNumbers()
    }

    fun numbersAsString(): String {
        return numbers.joinToString("") { it.asFourDigits() }
    }

    private fun Int.asFourDigits() = String.format("%04d", this)

    companion object {
        private fun generateRandomNumbers(): ArrayList<Int> {
            val numbersCount = 3 * 6
            val numbers = arrayListOf<Int>()
            for (i in 0 until numbersCount) {
                numbers.add(randomNumber())
            }
            return numbers
        }

        private fun randomNumber() = Random.nextInt(100, 9999)
    }

}