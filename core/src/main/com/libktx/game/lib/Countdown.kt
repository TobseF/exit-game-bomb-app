package com.libktx.game.lib

import kotlin.math.max

class Countdown(var minutes: Int = 0, private val seconds: Int = 0) : Resetable {

    private var finish = getNewEndTime()
    private var stoppedTime: Long? = null

    fun getCountdownTime(): ms {
        return getCountdownTime(stoppedTime ?: System.currentTimeMillis())
    }

    fun getContdownTimeSeconds() = getCountdownTime() / 1000

    private fun getCountdownTime(currentTime: ms): Long {
        val current = finish - currentTime
        return max(current, 0)
    }

    fun getTime(): Long = stoppedTime ?: System.currentTimeMillis()

    private fun getNewEndTime() = System.currentTimeMillis() + (minutes * 60 + seconds) * 1000

    fun isFinished() = getCountdownTime() == 0L

    fun isNotFinished() = !isFinished()

    fun stop() {
        stoppedTime = System.currentTimeMillis()
    }

    fun getFinishTime() = finish

    override fun reset() {
        finish = getNewEndTime()
        stoppedTime = null
    }

}
