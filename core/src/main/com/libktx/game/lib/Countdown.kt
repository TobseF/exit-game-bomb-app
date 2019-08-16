package com.libktx.game.lib

import kotlin.math.max

class Countdown(private val minutes: Int, private val seconds: Int = 0) : Resetable {

    private var finish = getEndTime()
    private var stoppedTime: Long? = null

    fun getContdownTime(): ms {
        return getContdownTime(stoppedTime ?: System.currentTimeMillis())
    }

    private fun getContdownTime(currentTime: ms): Long {
        val current = finish - currentTime
        return max(current, 0)
    }

    fun getTime(): Long = stoppedTime ?: System.currentTimeMillis()

    private fun getEndTime() = System.currentTimeMillis() + (minutes * 60 + seconds) * 1000

    fun isFinished() = getContdownTime() == 0L

    fun stop() {
        stoppedTime = System.currentTimeMillis()
    }

    override fun reset() {
        finish = getEndTime()
        stoppedTime = null
    }

}
