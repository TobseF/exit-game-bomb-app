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
import com.libktx.game.lib.Countdown
import com.libktx.game.lib.Timer
import com.libktx.game.lib.drawWrapped
import com.libktx.game.puzzle.NumbersPuzzleState
import com.libktx.game.puzzle.Puzzle
import ktx.graphics.use

class NumberPuzzleScreen(game: Game,
                         batch: Batch,
                         shapeRenderer: ShapeRenderer,
                         assets: AssetManager,
                         camera: OrthographicCamera,
                         countdown: Countdown,
                         val state: NumbersPuzzleState) :
        AbstractPuzzleScreen(Puzzle.Numbers, game, batch, assets, camera, shapeRenderer, countdown) {

    private val timer = Timer(2.5f, state::calculateNewNumbers)

    override fun render(delta: Float) {
        super.render(delta)
        timer.update(delta)

        batch.use {
            val loginFont = assets[FontAssets.NumbersBig]
            loginFont.drawWrapped(it, Color.BLACK, state.numbersAsString(), 40f, 440f, 520f)
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            state.calculateNewNumbers()
        }
    }

    override fun switchToNextScreen() {
        hide()
        countdown.stop()
        game.setScreen<SuccessScreen>()
    }

    override fun show() {

    }
}