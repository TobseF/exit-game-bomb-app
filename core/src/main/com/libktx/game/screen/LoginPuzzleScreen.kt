package com.libktx.game.screen

import com.badlogic.gdx.Gdx.input
import com.badlogic.gdx.Input.Keys.SPACE
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color.*
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType.Filled
import com.libktx.game.Game
import com.libktx.game.assets.FontAssets
import com.libktx.game.assets.SoundAssets
import com.libktx.game.assets.get
import com.libktx.game.lib.Countdown
import com.libktx.game.lib.circle
import com.libktx.game.lib.drawWithShadow
import com.libktx.game.lib.sensor.ILightSensor
import com.libktx.game.network.Endpoint
import ktx.graphics.use

class LoginPuzzleScreen(private val loginPuzzleState: LoginPuzzleState,
                        private val lightSensor: ILightSensor? = null,
                        game: Game,
                        batch: Batch,
                        shapeRenderer: ShapeRenderer,
                        assets: AssetManager,
                        camera: OrthographicCamera,
                        countdown: Countdown) :
        AbstractPuzzleScreen(Endpoint.Connect, game, batch, assets, camera, shapeRenderer, countdown) {

    /**
     * Small delay before bomb gets armed.
     */
    private val activeTimer = Countdown(seconds = 5)

    override fun render(delta: Float) {
        super.render(delta)

        activateBomb()

        shapeRenderer.use(Filled) {
            it.circle(DARK_GRAY, 280f, 250f, 126f)
            it.circle(BLUE, 280f, 250f, 120f)
        }

        batch.use {
            val loginFont = assets[FontAssets.ConsolasBig]
            loginFont.drawWithShadow(it, WHITE, "LOGIN", 217f, 260f)

            if (lightSensor != null) {
                loginFont.drawWithShadow(it, BLACK, "L:" + lightSensor.getCurrentLux(), 517f, 260f)
            }
        }

        if (input.isKeyJustPressed(SPACE)) {
            switchToNextScreen()
        }

    }

    /**
     * Play an alarm sound when the bomb gets activated by light
     */
    private fun activateBomb() {
        if (lightSensor != null && activeTimer.isFinished() && loginPuzzleState.isBomNotActivated() && lightSensor.getCurrentLux() > 1) {
            val sound = assets[SoundAssets.BombActivated]
            sound.play()
            loginPuzzleState.activateBomb()
        }
    }

    override fun switchToNextScreen() {
        hide()
        game.setScreen<NumberPuzzleScreen>()
    }

    override fun show() {
        activeTimer.reset()
    }
}