package com.libktx.game.screen

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType.Filled
import com.libktx.game.Game
import com.libktx.game.assets.FontAssets
import com.libktx.game.assets.SoundAssets
import com.libktx.game.assets.get
import com.libktx.game.lib.Countdown
import com.libktx.game.lib.TimerFormatter
import com.libktx.game.lib.rect
import com.libktx.game.network.Endpoint
import com.libktx.game.network.hue.HueService
import com.libktx.game.network.hue.HueService.HueValue.Red
import com.libktx.game.network.hue.HueService.LightState.OFF
import ktx.graphics.use
import java.util.*

/**
 * Plays the explosion sound and displays a back screen with an "destroyed" message.
 */
class ExplosionScreen(game: Game,
                      val bombState: BombState,
                      batch: Batch,
                      private val hueService: HueService,
                      shapeRenderer: ShapeRenderer,
                      assets: AssetManager,
                      camera: OrthographicCamera,
                      countdown: Countdown) : AbstractPuzzleScreen(Endpoint.GameOver, game, batch, assets, camera, shapeRenderer, countdown) {


    override fun switchToNextScreen() {
        resetGame()
    }

    private fun resetGame() {
        bombState.reset()
        game.reset()
    }

    override fun checkCountdown() {
        // We already exploded
    }

    override fun render(delta: Float) {
        super.render(delta)

        clearScreen(Color.BLACK)

        shapeRenderer.use(Filled) {
            it.rect(Color.WHITE, 330f, 350f, 140f, 30f)
        }
        batch.use {
            val font = assets[FontAssets.Consolas]
            font.color = Color.BLACK
            font.draw(it, "BOMB SYSTEM", 341f, 369f)
            font.color = Color.WHITE
            font.draw(it, "A fatal explosion has occurred at " + getTimeStamp(), 50f, 250f)
            font.draw(it, "The current bomb was terminated.", 50f, 220f)
        }

    }

    fun getTimeStamp() = TimerFormatter.getFormattedDateAsString(Date(countdown.getTime()))

    override fun show() {
        assets[SoundAssets.BombExplosion].play()
        hueService.setLights(Red, OFF)
    }
}