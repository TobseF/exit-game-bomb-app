package com.libktx.game.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.libktx.game.Game
import com.libktx.game.assets.FontAssets
import com.libktx.game.assets.get
import com.libktx.game.ecs.network.NetworkEvent
import com.libktx.game.ecs.network.NetworkEventListener
import com.libktx.game.lib.Countdown
import com.libktx.game.lib.Timer
import com.libktx.game.lib.drawWrapped
import ktx.graphics.use
import kotlin.random.Random

class NumberPuzzleScreen(game: Game,
                         batch: Batch,
                         shapeRenderer: ShapeRenderer,
                         assets: AssetManager,
                         camera: OrthographicCamera,
                         countdown: Countdown) : AbstractPuzzleScreen(game, batch, assets, camera, shapeRenderer, countdown), NetworkEventListener {

    override fun receivedNetworkEvent(networkEvent: NetworkEvent) {
    }

    private var numbers = generateRandomNumbers()
    private val timer = Timer(2.5f, this::calculateNewNumbers)

    override fun render(delta: Float) {
        super.render(delta)
        timer.update(delta)

        batch.use {
            val loginFont = assets[FontAssets.NumbersBig]
            loginFont.drawWrapped(it, Color.BLACK, numbersAsString(), 40f, 440f, 520f)
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            calculateNewNumbers()
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            hide()
            game.setScreen<EmptyPuzzleScreen>()
        }

    }

    private fun calculateNewNumbers() {
        numbers = generateRandomNumbers()
    }

    private fun numbersAsString(): String {
        return numbers.joinToString("") { it.asFourDigits() }
    }

    private fun Int.asFourDigits() = String.format("%04d", this)

    private fun generateRandomNumbers(): ArrayList<Int> {
        val numbersCount = 3 * 6
        val numbers = arrayListOf<Int>()
        for (i in 0 until numbersCount) {
            numbers.add(randomNumber())
        }
        return numbers
    }

    private fun randomNumber() = Random.nextInt(100, 9999)

    override fun show() {

    }
}