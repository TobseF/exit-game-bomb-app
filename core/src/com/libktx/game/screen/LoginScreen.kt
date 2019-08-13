package com.libktx.game.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color.BLUE
import com.badlogic.gdx.graphics.Color.WHITE
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType.Filled
import com.libktx.game.Game
import com.libktx.game.assets.FontAssets
import com.libktx.game.assets.get
import com.libktx.game.ecs.network.NetworkEvent
import com.libktx.game.ecs.network.NetworkEventListener
import com.libktx.game.lib.Countdown
import com.libktx.game.lib.TimerFormatter
import ktx.app.KtxScreen
import ktx.graphics.use

class LoginScreen(private val game: Game,
                  private val batch: Batch,
                  private val font: BitmapFont,
                  private val shapeRenderer: ShapeRenderer,
                  private val assets: AssetManager,
                  private val camera: OrthographicCamera,
                  private val countdown: Countdown) : KtxScreen, NetworkEventListener {


    override fun receivedNetworkEvent(networkEvent: NetworkEvent) {

    }

    override fun render(delta: Float) {
        // continue loading our assets
        assets.update()
        camera.update()
        batch.projectionMatrix = camera.combined

        shapeRenderer.begin(Filled)
        shapeRenderer.color = BLUE
        shapeRenderer.circle(280f, 250f, 120f)
        shapeRenderer.end()

        batch.use {
            val loginFont = assets[FontAssets.ConsolasBig]
            loginFont.color = WHITE
            loginFont.draw(it, "LOGIN ", 215f, 260f)

            val counterFont = assets[FontAssets.Counter]
            counterFont.color = WHITE
            counterFont.draw(it, TimerFormatter.getFormattedTimeAsString(countdown.getTime()), 645f, 450f)
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.removeScreen<LoginScreen>()
            dispose()
            game.setScreen<SampleGameScreen>()
        }


    }

    override fun show() {
    }
}