package com.libktx.game.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType.Filled
import com.libktx.game.Config
import com.libktx.game.Game
import com.libktx.game.assets.*
import com.libktx.game.lib.draw
import com.libktx.game.network.Network
import com.libktx.game.network.services.HueService
import ktx.app.KtxScreen
import ktx.graphics.use

/**
 * Black screen which loads all resources.
 */
class LoadingScreen(private val game: Game,
                    private val batch: Batch,
                    private val font: BitmapFont,
                    private val assets: AssetManager,
                    private val shape: ShapeRenderer,
                    private val hueService: HueService,
                    private val camera: OrthographicCamera) : KtxScreen {

    override fun show() {
        SoundAssets.values().forEach { assets.load(it) }
        FontAssets.values().forEach { assets.load(it) }
        MusicAssets.values().forEach { assets.load(it) }
        hueService.pair()
    }

    override fun render(delta: Float) {
        continueLoadingAssets()
        camera.update()
        batch.projectionMatrix = camera.combined

        shape.use(Filled) {
            it.color = Color.WHITE
            it.rect(97f, 183f, 69f, 21f)
        }

        batch.use {

            font.draw(it, Color.BLACK, "BombApp ", 100f, 200f)

            font.color = Color.WHITE
            font.draw(it, "IP: ${Network.ipAddress} Port: ${Config.serverPort}", 100f, 150f)

            if (assets.isFinished) {
                font.draw(it, "Tap anywhere to start!", 100f, 100f)
            } else {
                font.draw(it, "Loading assets...", 100f, 100f)
            }
        }

        if (triggeredNextScreen()) {
            game.setScreen<ConfigScreen>()
        }
    }

    private fun continueLoadingAssets() {
        assets.update()
    }

    private fun triggeredNextScreen() = (Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) && assets.isFinished
}