package com.libktx.game.lib

class Countdown(minutes: Int, seconds: Int = 0) {

    private var finish = System.currentTimeMillis() + (minutes * 60 + seconds) * 1000

    fun getTime(): ms {
        val current = finish - System.currentTimeMillis()
        return if (current <= 0) {
            0L
        } else {
            current
        }
    }
}
