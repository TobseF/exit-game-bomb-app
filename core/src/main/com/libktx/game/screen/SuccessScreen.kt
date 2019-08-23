package com.libktx.game.screen


import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.libktx.game.Game
import com.libktx.game.assets.FontAssets
import com.libktx.game.assets.SoundAssets
import com.libktx.game.assets.get
import com.libktx.game.lib.Countdown
import com.libktx.game.lib.TimeFormatter
import com.libktx.game.lib.draw
import com.libktx.game.lib.drawWithShadow
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
        clearScreen(Color.GREEN)
        batch.use {
            val counterFont = assets[FontAssets.CounterBig]
            counterFont.drawWithShadow(it, Color.GREEN, Color.DARK_GRAY, "88:88", 100f, 340f)
            counterFont.draw(it, Color.BLACK, getTimeAsString(), 100f, 340f)
            assets[FontAssets.ConsolasBig].draw(it, Color.WHITE, "UNLOCKED", 298f, 80f)
        }
    }

    override fun show() {
        assets[SoundAssets.BombDeactivated].play()
        hueService.setLights(HueValue.Green, ON)
        timerService.stop(countdown.getContdownTime())
    }

    private fun getTimeAsString() = TimeFormatter.getFormattedTimeAsString(countdown.getContdownTime())
}