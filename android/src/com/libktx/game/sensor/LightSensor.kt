package com.libktx.game.sensor

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import com.libktx.game.lib.sensor.ILightSensor


class LightSensor : ILightSensor, SensorEventListener {

    private var currentLux = 0f

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}

    override fun getCurrentLux() = currentLux

    override fun onSensorChanged(event: SensorEvent) {
        this.currentLux = event.values[0]
    }
}