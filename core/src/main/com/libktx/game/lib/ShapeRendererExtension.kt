package com.libktx.game.lib

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer

/** Draws a rectangle in the x/y plane using {@link ShapeType#Line} or {@link ShapeType#Filled}. */
fun ShapeRenderer.rect(color: Color, x: Float, y: Float, width: Float, height: Float) {
    this.color = color
    this.rect(x, y, width, height)
}

/** Draws a circle using {@link ShapeType#Line} or {@link ShapeType#Filled}. */
fun ShapeRenderer.circle(color: Color, x: Float, y: Float, radius: Float, segments: Int = 100) {
    this.color = color
    this.circle(x, y, radius, segments)
}
