package com.libktx.game.network

enum class Endpoint {
    Connect, Numbers, GameOver, Reset, Empty;

    val path: String
        get() = name.toLowerCase()

}