package com.libktx.game.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color.BLUE
import com.badlogic.gdx.graphics.Color.WHITE
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType.Filled
import com.libktx.game.Game
import com.libktx.game.assets.FontAssets
import com.libktx.game.assets.get
import com.libktx.game.ecs.network.NetworkEvent
import com.libktx.game.ecs.network.NetworkEventListener
import com.libktx.game.lib.Countdown
import ktx.graphics.use

class LoginPuzzleScreen(game: Game,
                        batch: Batch,
                        private val shapeRenderer: ShapeRenderer,
                        assets: AssetManager,
                        camera: OrthographicCamera,
                        countdown: Countdown) : AbstractPuzzleScreen(game, batch, assets, camera, countdown), NetworkEventListener {

    override fun receivedNetworkEvent(networkEvent: NetworkEvent) {
    }

    override fun render(delta: Float) {
        super.render(delta)

        shapeRenderer.begin(Filled)
        shapeRenderer.color = BLUE
        shapeRenderer.circle(280f, 250f, 120f)
        shapeRenderer.end()

        batch.use {
            val loginFont = assets[FontAssets.ConsolasBig]
            loginFont.color = WHITE
            loginFont.draw(it, "LOGIN", 215f, 260f)
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.removeScreen<LoginPuzzleScreen>()
            dispose()
            game.setScreen<NumberPuzzleScreen>()
        }

    }

    override fun show() {
    }
}