package com.libktx.game.screen

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Color.RED
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType.Filled
import com.libktx.game.Game
import com.libktx.game.assets.FontAssets
import com.libktx.game.assets.get
import com.libktx.game.ecs.network.PuzzleResponse
import com.libktx.game.ecs.network.ResponseStatus
import com.libktx.game.lib.Countdown
import com.libktx.game.lib.TimerFormatter
import com.libktx.game.lib.drawWithShadow
import com.libktx.game.puzzle.Puzzle
import ktx.app.KtxScreen
import ktx.graphics.use

/**
 * Renders a timer
 */
abstract class AbstractPuzzleScreen(val puzzle: Puzzle, protected val game: Game,
                                    protected val batch: Batch,
                                    protected val assets: AssetManager,
                                    protected val camera: OrthographicCamera,
                                    protected val shapeRenderer: ShapeRenderer,
                                    protected val countdown: Countdown) : KtxScreen {

    fun handlePuzzleResponse(response: PuzzleResponse) {
        if (response.status == ResponseStatus.OK) {
            switchToNextPuzzle()
        }
    }

    abstract fun switchToNextPuzzle()

    override fun render(delta: Float) {
        assets.update()
        camera.update()
        batch.projectionMatrix = camera.combined
        shapeRenderer.projectionMatrix = camera.combined

        renderCountdown()
    }

    private fun renderCountdown() {
        shapeRenderer.use(Filled) {
            it.color = Color.LIGHT_GRAY
            it.rect(0f, 0f, 800f, 480f)
        }

        shapeRenderer.use(Filled) {
            it.color = Color.GRAY
            it.rect(610f, 0f, 190f, 480f)
        }

        shapeRenderer.use(Filled) {
            it.color = Color.DARK_GRAY
            it.rect(610f, 380f, 190f, 100f)
        }


        batch.use {
            val counterFont = assets[FontAssets.Counter]
            counterFont.drawWithShadow(it, RED, getTimeAsString(), 645f, 450f)
        }

    }

    private fun getTimeAsString() = TimerFormatter.getFormattedTimeAsString(countdown.getTime())

    override fun show() {
    }
}