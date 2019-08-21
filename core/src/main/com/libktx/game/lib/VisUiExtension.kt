package com.libktx.game.lib

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.kotcrab.vis.ui.widget.VisTextButton
import com.kotcrab.vis.ui.widget.VisTextField
import kotlin.reflect.KMutableProperty0

fun VisTextField.bind(property: KMutableProperty0<String?>) {
    bind { property.set(it) }
}

fun VisTextField.bind(setter: (String) -> Any) {
    setTextFieldListener { textField, _ ->
        setter.invoke(textField.text)
    }
}

fun VisTextButton.setClickListener(runnable: () -> Any) {
    addCaptureListener(object : ClickListener() {
        override fun clicked(event: InputEvent, x: Float, y: Float) {
            runnable.invoke()
        }
    })
}