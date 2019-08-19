package com.libktx.game.network.hue

import com.libktx.game.Config
import io.github.zeroone3010.yahueapi.Hue
import io.github.zeroone3010.yahueapi.State
import java.awt.Color.PINK


class HueService {
    init {

        val appName = "de.its.game.bomb"
        val apiKey = Hue.hueBridgeConnectionBuilder(Config.hueBridgeIP).initializeApiConnection(appName)

        // Push the button on your Hue Bridge to resolve the apiKey future:
        val hue = Hue(Config.hueBridgeIP, apiKey.get())

        // Get a room -- returns Optional.empty() if the room does not exist, but
        // let's assume we know for a fact it exists and can do the .get() right away:
        val room = hue.getRoomByName("Bomb").get()

        // Turn the lights on, make them pink:
        room.setState(State.builder().color(PINK).on())

    }
}