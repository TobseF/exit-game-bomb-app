package com.libktx.game

object Config {
    const val ServerPort = 5000
    const val TimerPort = 5001

    const val defaultCountDownTime = 42

    const val appIdentifier = "de.its.game.bomb"

    val screenSize = Rect(800, 480)

    data class Rect(val width: Float, val height: Float) {
        constructor(width: Int, height: Int) : this(width.toFloat(), height.toFloat())
    }

}
