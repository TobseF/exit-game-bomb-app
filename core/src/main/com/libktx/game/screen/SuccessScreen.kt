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
import com.libktx.game.lib.TimerFormatter
import com.libktx.game.lib.draw
import com.libktx.game.lib.drawWithShadow
import com.libktx.game.network.Endpoint
import com.libktx.game.network.hue.HueService
import com.libktx.game.network.hue.HueService.HueValue
import com.libktx.game.network.hue.HueService.LightState.ON
import ktx.graphics.use

class SuccessScreen(game: Game,
                    private val hueService: HueService,
                    batch: Batch,
                    shapeRenderer: ShapeRenderer,
                    assets: AssetManager,
                    camera: OrthographicCamera,
                    countdown: Countdown) :
        AbstractPuzzleScreen(Endpoint.Empty, game, batch, assets, camera, shapeRenderer, countdown) {


    override fun switchToNextScreen() {
        hide()
        game.setScreen<ExplosionScreen>()
    }


    override fun checkCountdown() {
        // We already exploded
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
    }

    private fun getTimeAsString() = TimerFormatter.getFormattedTimeAsString(countdown.getContdownTime())
}