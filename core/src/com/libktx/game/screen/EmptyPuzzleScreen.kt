package com.libktx.game.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color.BLACK
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.libktx.game.Game
import com.libktx.game.assets.FontAssets
import com.libktx.game.assets.get
import com.libktx.game.lib.Countdown
import com.libktx.game.lib.draw
import com.libktx.game.puzzle.Puzzle
import ktx.graphics.use

class EmptyPuzzleScreen(game: Game,
                        batch: Batch,
                        shapeRenderer: ShapeRenderer,
                        assets: AssetManager,
                        camera: OrthographicCamera,
                        countdown: Countdown) : AbstractPuzzleScreen(Puzzle.NextPuzzle, game, batch, assets, camera, shapeRenderer, countdown) {

    override fun switchToNextPuzzle() {

    }


    override fun render(delta: Float) {
        super.render(delta)

        batch.use {
            val loginFont = assets[FontAssets.ConsolasBig]
            loginFont.draw(it, BLACK, "Next Puzzle", 160f, 260f)
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            hide()
            game.setScreen<LoginPuzzleScreen>()
        }

    }

    override fun show() {
    }
}