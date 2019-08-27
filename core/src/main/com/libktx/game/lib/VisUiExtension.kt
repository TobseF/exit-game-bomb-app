package com.libktx.game.lib

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.kotcrab.vis.ui.widget.VisTextButton
import com.kotcrab.vis.ui.widget.VisTextField
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

fun VisTextButton.setClickListener(runnable: () -> Unit) {
    addCaptureListener(object : ClickListener() {
        override fun clicked(event: InputEvent, x: Float, y: Float) {
            runnable.invoke()
        }
    })
}

fun ProgressBar.addValueChangeListener(valueChangeEvent: (Float) -> Unit) {
    addListener(
            object : ChangeListener() {
                override fun changed(event: ChangeEvent, actor: Actor) {
                    val value = (actor as ProgressBar).value
                    valueChangeEvent.invoke(value)
                }
            })
}