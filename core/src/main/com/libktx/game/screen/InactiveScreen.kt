package com.libktx.game.screen


import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.libktx.game.Game
import com.libktx.game.Preferences
import com.libktx.game.assets.MusicAssets
import com.libktx.game.assets.get
import com.libktx.game.lib.Countdown
import com.libktx.game.lib.draw
import com.libktx.game.lib.sensor.ILightSensor
import com.libktx.game.network.Endpoint
import com.libktx.game.network.services.HueService
import com.libktx.game.network.services.HueService.HueValue
import com.libktx.game.network.services.HueService.LightState.OFF
import com.libktx.game.network.services.HueService.LightState.ON
import com.libktx.game.network.services.TimerService
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ktx.graphics.use

typealias async = ktx.async.KtxAsync

/**
 * Inactive bomb, waiting to get activated by light.
 * On activation it plays an activated sound an switches to the first puzzle.
 */
class InactiveScreen(private val lightSensor: ILightSensor? = null,
                     game: Game,
                     private val bombState: BombState,
                     private val hueService: HueService,
                     private val timerService: TimerService,
                     private val font: BitmapFont,
                     batch: Batch,
                     shapeRenderer: ShapeRenderer,
                     assets: AssetManager,
                     camera: OrthographicCamera,
                     countdown: Countdown) :
        AbstractPuzzleScreen(Endpoint.Empty, game, batch, assets, camera, shapeRenderer, countdown) {

    /**
     * Small delay before bomb gets armed.
     */
    private val activeTimer = Countdown(seconds = 10)

    override fun switchToNextScreen() {
        switchToFirstPuzzle()
        if (bombState.isBombNotActivated()) { // for debugging
            activateBomb()
        }
    }

    private fun switchToFirstPuzzle() {
        game.setScreen<LoginPuzzleScreen>()
    }

    /**
     * Checks if the bomb was activated by an external light source
     */
    private fun tryToActivateBomb() {
        if (activeTimer.isFinished() && bombState.isBombNotActivated()) {
            if (isInBrightEnvironment(lightSensor)) {
                activateBomb()
            }
        }
    }

    private fun isInBrightEnvironment(lightSensor: ILightSensor?) = lightSensor == null || lightSensor.getCurrentLux() > 1

    /**
     * Play an alarm sound when the bomb gets activated by light. Changes the hue lights to red
     */
    private fun activateBomb() {
        val sound: Music = assets[MusicAssets.BombActivated]
        sound.play()
        blinkLights()
        if(Preferences.enableExternalTimer){
            timerService.start(countdown.getCountdownTime())
        }

        bombState.activateBomb()
        switchToFirstPuzzle()
    }

    private fun blinkLights() {
        val delay = 800L
        async.launch {
            hueLightsOn()
            delay(delay)
            hueLightsOff()
            delay(delay)
            hueLightsOn()
            delay(delay)
            hueLightsOff()
            delay(delay)
            hueLightsOn()
        }
    }

    private fun hueLightsOff() {
        hueService.setLights(HueValue.White, OFF, transitionTime = 1)
    }

    private fun hueLightsOn() {
        hueService.setLights(HueValue.Red, ON, transitionTime = 2)
    }

    override fun show() {
        Gdx.app.input.inputProcessor = null
        activeTimer.reset()
        hueService.setLights(HueValue.White, ON, 85)
        if(Preferences.enableExternalTimer){
            timerService.disable()
        }
    }

    override fun render(delta: Float) {
        super.render(delta)
        tryToActivateBomb()
        clearScreen(Color.BLACK)

        batch.use {
            val status = if (activeTimer.isFinished()) "Waiting ..." else "Active in " + activeTimer.getContdownTimeSeconds()
            font.draw(it, Color.LIGHT_GRAY, status, 100f, 100f)
        }

    }

}