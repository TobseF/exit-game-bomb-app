package com.libktx.game.ecs.network

import java.util.*

data class NetworkEvent (val endpoint: String, val data:String, val time: Date = Date())