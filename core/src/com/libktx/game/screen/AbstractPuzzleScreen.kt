package com.libktx.game.screen

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color.WHITE
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.libktx.game.Game
import com.libktx.game.assets.FontAssets
import com.libktx.game.assets.get
import com.libktx.game.ecs.network.NetworkEvent
import com.libktx.game.ecs.network.NetworkEventListener
import com.libktx.game.lib.Countdown
import com.libktx.game.lib.TimerFormatter
import ktx.app.KtxScreen
import ktx.graphics.use

/**
 * Renders a timer
 */
abstract class AbstractPuzzleScreen(protected val game: Game,
                                    protected val batch: Batch,
                                    protected val assets: AssetManager,
                                    protected val camera: OrthographicCamera,
                                    protected val countdown: Countdown) : KtxScreen, NetworkEventListener {


    override fun receivedNetworkEvent(networkEvent: NetworkEvent) {
    }

    override fun render(delta: Float) {
        assets.update()
        camera.update()
        batch.projectionMatrix = camera.combined

        batch.use {
            val counterFont = assets[FontAssets.Counter]
            counterFont.color = WHITE
            counterFont.draw(it, TimerFormatter.getFormattedTimeAsString(countdown.getTime()), 645f, 450f)
        }

    }

    override fun show() {
    }
}