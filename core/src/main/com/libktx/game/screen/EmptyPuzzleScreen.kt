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
import com.libktx.game.network.Endpoint
import ktx.graphics.use

/**
 * Not active, can be used as place holder for the next puzzle.
 */
class EmptyPuzzleScreen(game: Game,
                        batch: Batch,
                        shapeRenderer: ShapeRenderer,
                        assets: AssetManager,
                        camera: OrthographicCamera,
                        countdown: Countdown) : AbstractPuzzleScreen(Endpoint.Empty, game, batch, assets, camera, shapeRenderer, countdown) {

    override fun switchToNextScreen() {}

    override fun render(delta: Float) {
        super.render(delta)

        batch.use {
            val loginFont = assets[FontAssets.ConsolasBig]
            loginFont.draw(it, BLACK, "Next Puzzle", 160f, 260f)
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.setScreen<LoginPuzzleScreen>()
        }

    }

    override fun show() {}
}