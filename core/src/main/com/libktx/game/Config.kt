package com.libktx.game

/**
 * Configuration constants
 * @see [Preferences]
 */
object Config {
    const val serverPort = 5000
    const val timerPort = 5001

    /**
     * Default time before the bomb explodes in minutes
     */
    const val defaultCountDownTime = 42

    const val appIdentifier = "de.its.game.bomb"

    val screenSize = Rect(800, 480)

    data class Rect(val width: Float, val height: Float) {
        constructor(width: Int, height: Int) : this(width.toFloat(), height.toFloat())
    }

}
