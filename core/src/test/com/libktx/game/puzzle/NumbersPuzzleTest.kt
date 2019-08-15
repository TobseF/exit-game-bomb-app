package com.libktx.game.puzzle


import io.kotlintest.shouldBe
import io.kotlintest.specs.FunSpec


internal class NumbersPuzzleTest : FunSpec({

    test("Numbers as String are formatted to 4 digit dates") {
        NumbersPuzzleState(listOf(2020, 1987, 2001, 1990)).numbersAsString() shouldBe "2020198720011990"
        NumbersPuzzleState(listOf(2, 20, 200, 2000)).numbersAsString() shouldBe "0002002002002000"
    }

    test("SortedNumbers are sorted 3 digit dates") {
        NumbersPuzzle(NumbersPuzzleState(listOf(2020, 1987, 2001, 1990))).getSortedNumbers() shouldBe "1987199020012020"
        NumbersPuzzle(NumbersPuzzleState(listOf(200, 20, 2, 2000))).getSortedNumbers() shouldBe "0002002002002000"
    }

})