package com.libktx.game.screen

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType.Filled
import com.libktx.game.Game
import com.libktx.game.assets.SoundAssets
import com.libktx.game.assets.get
import com.libktx.game.lib.Countdown
import com.libktx.game.network.Endpoint
import ktx.graphics.use

class SuccessScreen(game: Game,
                    batch: Batch,
                    shapeRenderer: ShapeRenderer,
                    assets: AssetManager,
                    camera: OrthographicCamera,
                    countdown: Countdown) :
        AbstractPuzzleScreen(Endpoint.Empty, game, batch, assets, camera, shapeRenderer, countdown) {

    override fun switchToNextScreen() {
        // This is the end
    }

    override fun checkCountdown() {
        // We already exploded
    }

    override fun render(delta: Float) {
        super.render(delta)

        shapeRenderer.use(Filled) {
            it.color = Color.GREEN
            it.rect(0f, 0f, 610f, 480f)
        }

    }

    override fun show() {
        assets[SoundAssets.BombDeactivated].play()
    }
}