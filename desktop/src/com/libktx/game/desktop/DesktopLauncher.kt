package com.libktx.game.desktop

import com.badlogic.gdx.Application
import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.libktx.game.Game

object DesktopLauncher {
    @JvmStatic
    fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration().apply {
            title = "Bomb"
            width = 800
            height = 480
            samples = 3
        }
        val game = Game()
        LwjglApplication(game, config).logLevel = Application.LOG_DEBUG
        Server(game).start()
    }
}
