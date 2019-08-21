package com.libktx.game.screen

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import ktx.app.KtxScreen

abstract class AbstractScreen(protected val batch: Batch,
                              protected val assets: AssetManager,
                              protected val camera: OrthographicCamera,
                              protected val shapeRenderer: ShapeRenderer) : KtxScreen {

    override fun render(delta: Float) {
        assets.update()
        camera.update()
        batch.projectionMatrix = camera.combined
        shapeRenderer.projectionMatrix = camera.combined
    }

    fun clearScreen(color: Color) {
        ktx.app.clearScreen(color.r, color.g, color.b, color.a)
    }

    override fun show() {
    }
}