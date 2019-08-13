package com.libktx.game.ecs.component

import com.badlogic.ashley.core.Component
import com.libktx.game.ecs.network.NetworkEvent
import ktx.ashley.mapperFor

class BucketComponent : Component {
    companion object {
        val mapper = mapperFor<BucketComponent>()
    }

    var dropsGathered = 0
    var lastNetworkEvent: NetworkEvent? = null
}