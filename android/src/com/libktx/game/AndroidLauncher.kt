package com.libktx.game

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.view.WindowManager
import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.libktx.game.sensor.LightSensor

class AndroidLauncher : AndroidApplication() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        val config = AndroidApplicationConfiguration().apply {
            numSamples = 2
        }

        val game = Game(createLightSensor())
        initialize(game, config)
        Server(game).start()
    }

    private fun createLightSensor(): LightSensor {
        val lightSensor = LightSensor()
        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        sensorManager.registerListener(lightSensor, sensor, SensorManager.SENSOR_DELAY_GAME)
        return lightSensor
    }
}
