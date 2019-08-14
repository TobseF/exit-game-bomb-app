package com.libktx.game.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color.*
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
import com.libktx.game.lib.drawWithShadow
import ktx.graphics.use

class LoginPuzzleScreen(game: Game,
                        batch: Batch,
                        shapeRenderer: ShapeRenderer,
                        assets: AssetManager,
                        camera: OrthographicCamera,
                        countdown: Countdown) : AbstractPuzzleScreen(game, batch, assets, camera, shapeRenderer, countdown), NetworkEventListener {

    override fun receivedNetworkEvent(networkEvent: NetworkEvent) {
    }

    override fun render(delta: Float) {
        super.render(delta)

        shapeRenderer.use(Filled) {
            it.color = DARK_GRAY
            it.circle(280f, 250f, 126f, 100)
            it.color = BLUE
            it.circle(280f, 250f, 120f, 100)
        }

        batch.use {
            val loginFont = assets[FontAssets.ConsolasBig]
            loginFont.drawWithShadow(it, WHITE, "LOGIN", 217f, 260f)
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