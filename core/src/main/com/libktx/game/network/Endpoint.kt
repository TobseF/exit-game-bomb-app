package com.libktx.game.network

/**
 * Paths for the REST endpoints. The lowercase name will be used as path.
 */
enum class Endpoint {
    Connect, Numbers, GameOver, Reset, Empty;

    companion object {
        private val pathToEndpoint = values().map { it.path to it }.toMap()
        operator fun get(path: String): Endpoint? = pathToEndpoint[path]
    }

    val path: String
        get() = name.toLowerCase()

}