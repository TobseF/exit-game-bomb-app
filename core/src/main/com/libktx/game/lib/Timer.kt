package com.libktx.game.lib

/**
 * @param actionTime seconds before the action will be triggered
 */
class Timer(var actionTime: Float, private val timerAction: (() -> Unit)?) : Time {

    override var time = 0F
    private var pause = false

    fun update(deltaTime: Float) {
        time += deltaTime
        if (!pause && time >= actionTime) {
            time = 0F
            timerAction?.invoke()
        }
    }

    fun togglePause() {
        pause = !pause
    }

    fun reset() {
        time = 0F
    }

}
