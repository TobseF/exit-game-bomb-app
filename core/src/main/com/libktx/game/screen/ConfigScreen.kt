package com.libktx.game.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.kotcrab.vis.ui.widget.VisProgressBar
import com.kotcrab.vis.ui.widget.VisTextField
import com.libktx.game.Config
import com.libktx.game.Game
import com.libktx.game.Preferences
import com.libktx.game.lib.bind
import com.libktx.game.lib.rect
import com.libktx.game.lib.sensor.ILightSensor
import com.libktx.game.lib.setClickListener
import com.libktx.game.network.Network
import com.libktx.game.network.hue.HueService
import ktx.graphics.use
import ktx.vis.table

/**
 * Config screen for the basic settings.
 * Shows some info like the bombs IP address.
 */
class ConfigScreen(private val lightSensor: ILightSensor? = null,
                   private val hueService: HueService,
                   val game: Game,
                   batch: Batch,
                   shapeRenderer: ShapeRenderer,
                   assets: AssetManager,
                   camera: OrthographicCamera) :
        AbstractScreen(batch, assets, camera, shapeRenderer) {

    private val stage: Stage = Stage(FitViewport(Config.screenSize.width, Config.screenSize.height))

    private lateinit var lightLabel: VisTextField
    private lateinit var lightBar: VisProgressBar

    override fun render(delta: Float) {
        super.render(delta)
        shapeRenderer.use(ShapeRenderer.ShapeType.Filled) {
            it.rect(Color.LIGHT_GRAY, 0f, 0f, Config.screenSize.width, Config.screenSize.height)
        }

        if (lightSensor != null) {
            val currentLux = lightSensor.getCurrentLux()
            lightLabel.text = currentLux.toString()
            lightBar.value = currentLux
        }

        stage.act(delta)
        stage.draw()
    }


    private fun switchToNextScreen() {
        hide()
        game.setScreen<InactiveScreen>()
    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height)
    }

    override fun show() {
        val table = table(true) {
            defaults().center()
            setPosition(350f, 250f)

            label("Light:")
            lightLabel = textField {
                isDisabled = true
            }.cell(grow = false)
            lightBar = progressBar(0f, 800f)

            row()

            label("Bomb IP:")
            textField(Network.ipAddress) {
                isDisabled = true
            }.cell(grow = true)

            row()

            label("Bomb Port:")
            textField(Config.ServerPort.toString()) {
                isDisabled = true
            }.cell(grow = true)

            row()

            label("Hue IP:")
            textField {
                bind(Preferences::hueIp)
            }.cell(grow = true)
            row()

            label("Hue Room: ")
            textField {
                bind(Preferences::hueRoomName)
            }.cell(grow = true)

            row()

            label("Hue Key: ")
            val labelApiKey = textField {
                bind(Preferences::hueApiKey)
                isDisabled = true
            }.cell(grow = true)

            row()

            val pairedCheckBox = checkBox("Paired") {
                isDisabled = true
                isChecked = hueService.isPaired()
            }.cell(colspan = 2)
            row()
            textButton("Reset") {
                setClickListener { resetHueSettings() }
            }.cell(fill = true)
            row()
            val buttonPair = textButton("Pair").cell(fill = true)
            val state = label("")

            buttonPair.setClickListener {
                state.setText("Pairing ...")
                pairedCheckBox.isChecked = hueService.pair()
                state.clear()
                val hueApiKey: String? = Preferences.hueApiKey
                if (hueApiKey != null) {
                    labelApiKey.text = hueApiKey
                }
            }

            row()
            textButton("Start") {
                setClickListener { switchToNextScreen() }
            }.cell(minWidth = 120f, minHeight = 45f, align = Align.right, colspan = 3)
        }

        Gdx.app.input.inputProcessor = stage
        stage.addActor(table)
        //stage.isDebugAll = true
        stage.viewport.centerCamera()
    }

    private fun resetHueSettings() {
        println("Reset hueApiKey")
        Preferences.hueApiKey = null
    }

    private fun Viewport.centerCamera() {
        camera.position.set(worldWidth / 2, worldHeight / 2, 0f)
        camera.update()
    }
}