package com.libktx.game.lib

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.BitmapFontCache
import com.badlogic.gdx.graphics.g2d.GlyphLayout

/** Draws text at the specified position with a shadow.
 * @see BitmapFontCache.addText
 */
fun BitmapFont.drawWithShadow(batch: Batch, color: Color, shadow: Color, str: CharSequence, x: Float, y: Float): GlyphLayout {
    draw(batch, shadow, str, x + 2, y + 1)
    return draw(batch, color, str, x, y)
}

/** Draws text at the specified position with a shadow.
 * @see BitmapFontCache.addText
 */
fun BitmapFont.drawWithShadow(batch: Batch, color: Color, str: CharSequence, x: Float, y: Float): GlyphLayout {
    return this.drawWithShadow(batch, color, Color.BLACK, str, x, y)
}

/** Draws text at the specified position.
 * @see BitmapFontCache.addText
 */
fun BitmapFont.drawWrapped(batch: Batch, color: Color, str: CharSequence, x: Float, y: Float, targetWidth: Float): GlyphLayout {
    this.color = color
    return this.draw(batch, str, x, y, targetWidth, 0, true)
}

/** Draws text at the specified position.
 * @see BitmapFontCache.addText
 */
fun BitmapFont.draw(batch: Batch, color: Color, str: CharSequence, x: Float, y: Float): GlyphLayout {
    this.color = color
    return this.draw(batch, str, x, y)
}