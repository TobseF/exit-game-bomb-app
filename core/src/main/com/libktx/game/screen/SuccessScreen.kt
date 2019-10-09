package com.libktx.game.screen


import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType.Filled
import com.libktx.game.Game
import com.libktx.game.Preferences
import com.libktx.game.assets.FontAssets
import com.libktx.game.assets.ImageAssets
import com.libktx.game.assets.SoundAssets
import com.libktx.game.assets.get
import com.libktx.game.lib.Countdown
import com.libktx.game.lib.TimeFormatter
import com.libktx.game.lib.draw
import com.libktx.game.lib.rect
import com.libktx.game.network.Endpoint
import com.libktx.game.network.services.HueService
import com.libktx.game.network.services.HueService.HueValue
import com.libktx.game.network.services.HueService.LightState.ON
import com.libktx.game.network.services.TimerService
import ktx.graphics.use

class SuccessScreen(game: Game,
                    private val hueService: HueService,
                    private val timerService: TimerService,
                    batch: Batch,
                    shapeRenderer: ShapeRenderer,
                    assets: AssetManager,
                    camera: OrthographicCamera,
                    countdown: Countdown) :
        AbstractPuzzleScreen(Endpoint.Empty, game, batch, assets, camera, shapeRenderer, countdown) {


    override fun switchToNextScreen() {
        game.setScreen<ExplosionScreen>()
    }

    override fun checkCountdown() {
        // We got it - we can stop
    }

    override fun render(delta: Float) {
        super.render(delta)

        clearScreen(Color.LIGHT_GRAY)

        shapeRenderer.use(Filled) {
            it.rect(Color.GREEN, 109f, 329f, 583f, 107f)
        }
        batch.use {

            it.draw(assets[ImageAssets.Solution], 109f, 23f)
            assets[FontAssets.ConsolasBig].draw(it, Color.WHITE, "UNLOCKED", 298f, 393f)
        }
    }

    override fun show() {
        setFixedNumbersFontWidth()

        assets[SoundAssets.BombDeactivated].play()
        hueService.setLights(HueValue.Green, ON)
        if(Preferences.enableExternalTimer){
            timerService.stop(countdown.getCountdownTime())
        }
    }

    /**
     * Ensure all numbers in the font have the same width. Otherwise a 20:00 is longer than 10:00!
     */
    private fun setFixedNumbersFontWidth() {
        val counterFont = assets[FontAssets.CounterBig]
        (0..9).forEach { counterFont.setFixedWidthGlyphs(it.toString()) }
    }

    private fun getTimeAsString() = TimeFormatter.getFormattedTimeAsString(countdown.getCountdownTime())
}