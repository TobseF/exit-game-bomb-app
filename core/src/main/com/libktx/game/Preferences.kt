package com.libktx.game

import com.badlogic.gdx.Gdx
import com.libktx.game.Preferences.Preference.*
import kotlin.reflect.KProperty

object Preferences {

    private val preferences = Gdx.app.getPreferences(Config.appIdentifier)

    private enum class Preference { HueIP, HueRoomName, HueApiKey }

    private fun get(preference: Preference): String? = preferences.getString(preference.name)
    private fun get(preference: Preference, default: String): String = preferences.getString(preference.name, default)

    private fun save(preference: Preference, value: String?) {
        if (value == null) {
            preferences.remove(preference.name)
        } else {
            preferences.putString(preference.name, value)
            preferences.flush()
        }
    }

    var hueIp: String? by Delegate(HueIP)

    var hueRoomName: String? by Delegate(HueRoomName)

    var hueApiKey: String? by Delegate(HueApiKey)

    private class Delegate(private val prefKey: Preference) {

        operator fun getValue(thisRef: Any?, property: KProperty<*>): String? {
            val value = get(prefKey)
            return if (value != null) {
                if (value.isEmpty()) null else value
            } else {
                null
            }
        }

        operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String?) {
            save(prefKey, value)
        }
    }
}