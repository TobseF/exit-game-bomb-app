package com.libktx.game.lib

class Countdown(private val minutes: Int, private val seconds: Int = 0) {

    private var finish = getEndTime()
    private var stoppedTime: Long? = null


    fun getTime(): ms {
        if (stoppedTime != null) {
            return stoppedTime!!
        }

        val current = finish - System.currentTimeMillis()
        return if (current <= 0) {
            0L
        } else {
            current
        }
    }

    private fun getEndTime() = System.currentTimeMillis() + (minutes * 60 + seconds) * 1000

    fun isFinished() = getTime() == 0L

    fun stop() {
        stoppedTime = getTime()
    }

    fun reset() {
        finish = getEndTime()
    }

}
