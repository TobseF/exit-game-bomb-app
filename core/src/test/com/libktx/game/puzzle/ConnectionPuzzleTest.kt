package com.libktx.game.puzzle


import com.libktx.game.network.PuzzleResponse
import io.kotlintest.shouldBe
import io.kotlintest.specs.FunSpec


internal class ConnectionPuzzleTest : FunSpec({

    test("Solve login puzzle by 'goin'") {
        ConnectPuzzle().request("goin") shouldBe PuzzleResponse.OK
    }

    test("Fail the login puzzle") {
        ConnectPuzzle().request("connect") shouldBe PuzzleResponse.FALSE
        ConnectPuzzle().request("login") shouldBe PuzzleResponse.FALSE
    }

})
