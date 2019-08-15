package com.libktx.game.puzzle


import com.libktx.game.network.PuzzleResponse
import io.kotlintest.shouldBe
import io.kotlintest.specs.FunSpec


internal class ConnectionPuzzleTest : FunSpec({

    test("Solve login puzzle by 'goin'") {
        LoginPuzzle().request("goin") shouldBe PuzzleResponse.OK
    }

    test("Fail the login puzzle") {
        LoginPuzzle().request("connect") shouldBe PuzzleResponse.FALSE
        LoginPuzzle().request("login") shouldBe PuzzleResponse.FALSE
    }

})
