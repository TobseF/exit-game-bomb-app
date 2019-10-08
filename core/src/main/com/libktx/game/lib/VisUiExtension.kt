package com.libktx.game.lib

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.kotcrab.vis.ui.widget.VisImage
import com.kotcrab.vis.ui.widget.VisTextField
import ktx.vis.KVisTable
import kotlin.reflect.KMutableProperty0

fun VisTextField.bind(property: KMutableProperty0<String?>) {
    bind { property.set(it) }
}

fun VisTextField.bindInt(property: KMutableProperty0<Int>) {
    bind { property.set(it.toInt()) }
}

fun VisTextField.bind(setter: (String) -> Unit) {
    setTextFieldListener { textField, _ ->
        setter.invoke(textField.text)
    }
}

fun KVisTable.colorPreview(color: Color = Color.WHITE, size: Float = 20f): VisImage {
    return this.image("white") {
        width = size
        height = size
        this.color = color
    }
}

fun TextButton.setClickListener(runnable: () -> Unit) {
    addCaptureListener(object : ChangeListener() {
        override fun changed(event: ChangeEvent, actor: Actor) {
            runnable.invoke()
        }
    })
}

fun ProgressBar.addValueChangeListener(valueChangeEvent: (Float) -> Unit) {
    addListener(object : ChangeListener() {
        override fun changed(event: ChangeEvent, actor: Actor) {
            val value = (actor as ProgressBar).value
            valueChangeEvent.invoke(value)
        }
    })
}