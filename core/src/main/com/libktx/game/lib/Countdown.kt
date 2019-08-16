package com.libktx.game.lib

import kotlin.math.max

class Countdown(private val minutes: Int, private val seconds: Int = 0) : Resetable {

    private var finish = getEndTime()
    private var stoppedTime: Long? = null

    fun getTime(): ms {
        if (stoppedTime != null) {
            return stoppedTime!!
        }
        val current = finish - System.currentTimeMillis()
        return max(current, 0)
    }

    private fun getEndTime() = System.currentTimeMillis() + (minutes * 60 + seconds) * 1000

    fun isFinished() = getTime() == 0L

    fun stop() {
        stoppedTime = getTime()
    }

    override fun reset() {
        finish = getEndTime()
        stoppedTime = null
    }

}
