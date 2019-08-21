package com.libktx.game

object Config {
    const val ServerPort = 5000
    /**
     * Time in minutes of a game
     */
    const val countdownTime = 20

    const val appIdentifier = "de.its.game.bomb"

    val screenSize = Rect(800, 480)

    data class Rect(val width: Float, val height: Float) {
        constructor(width: Int, height: Int) : this(width.toFloat(), height.toFloat())
    }

}
