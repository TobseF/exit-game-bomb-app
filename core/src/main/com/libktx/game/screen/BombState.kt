package com.libktx.game.screen

import com.libktx.game.lib.Resetable

class BombState : Resetable {

    var bombActivated = false

    override fun reset() {
        bombActivated = false
    }

    fun activateBomb() {
        bombActivated = true
    }

    fun isBomNotActivated() = !bombActivated

}