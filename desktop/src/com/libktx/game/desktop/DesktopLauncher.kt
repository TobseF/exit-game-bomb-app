package com.libktx.game.desktop

import com.badlogic.gdx.Application
import com.badlogic.gdx.Files
import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.libktx.game.Config
import com.libktx.game.Game
import com.libktx.game.assets.Icons

object DesktopLauncher {
    @JvmStatic
    fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration().apply {
            title = "Bomb"
            width = Config.screenSize.width.toInt()
            height = Config.screenSize.height.toInt()
            resizable = false
            samples = 3
            addIcon(Icons.AppIcon16)
            addIcon(Icons.AppIcon64)
        }
        val game = Game()
        LwjglApplication(game, config).logLevel = Application.LOG_DEBUG
        Server(game).start()
    }


    private fun LwjglApplicationConfiguration.addIcon(icon: Icons) {
        this.addIcon(icon.path, Files.FileType.Internal)
    }
}
