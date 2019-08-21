package com.libktx.game.lib

import com.badlogic.gdx.graphics.OrthographicCamera
import com.libktx.game.Config

/**
 * Sets this camera to an orthographic projection, centered at (viewportWidth/2, viewportHeight/2), with the y-axis pointing up or down.
 */
fun OrthographicCamera.setToOrtho(size: Config.Rect) = this.setToOrtho(false, size.width, size.height)