package com.libktx.game.screen

import com.libktx.game.lib.Resetable
import com.libktx.game.network.Endpoint
import com.libktx.game.network.Endpoint.Connect
import com.libktx.game.network.Endpoint.Numbers

class BombState : Resetable {

    private var bombActivated = false

    /**
     * Available puzzles.
     */
    private val puzzles = listOf(Connect, Numbers)

    /**
     * Active puzzle. Only this can be solved.
     */
    var currentPuzzle = puzzles.first()
        set(value) {
            if (value !in puzzles) {
                throw IllegalStateException("Changed to an unknown puzzle")
            }
            field = value
        }


    fun isPuzzle(endpoint: Endpoint) = puzzles.contains(endpoint)

    override fun reset() {
        bombActivated = false
        puzzles.first()
    }

    fun activateBomb() {
        bombActivated = true
    }

    fun isBombNotActivated() = !bombActivated

}