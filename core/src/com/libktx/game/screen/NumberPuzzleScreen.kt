package com.libktx.game.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color.WHITE
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.libktx.game.Game
import com.libktx.game.assets.FontAssets
import com.libktx.game.assets.get
import com.libktx.game.ecs.network.NetworkEvent
import com.libktx.game.ecs.network.NetworkEventListener
import com.libktx.game.lib.Countdown
import ktx.graphics.use
import kotlin.random.Random

class NumberPuzzleScreen(game: Game,
                         batch: Batch,
                         val shapeRenderer: ShapeRenderer,
                         assets: AssetManager,
                         camera: OrthographicCamera,
                         countdown: Countdown) : AbstractPuzzleScreen(game, batch, assets, camera, countdown), NetworkEventListener {

    override fun receivedNetworkEvent(networkEvent: NetworkEvent) {
    }

    private var numbers = generateRandomNumbers()

    override fun render(delta: Float) {
        super.render(delta)

        batch.use {
            val loginFont = assets[FontAssets.NumbersBig]
            loginFont.color = WHITE
            loginFont.draw(it, numbersAsString(), 60f, 440f, 520f, 0, true)
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            calculateNewNubers()
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.removeScreen<NumberPuzzleScreen>()
            dispose()
            game.setScreen<SampleGameScreen>()
        }

    }

    private fun calculateNewNubers() {
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

    fun randomNumber() = Random.nextInt(100, 9999)

    override fun show() {

    }
}