package com.libktx.game.desktop

import com.libktx.game.Config
import com.libktx.game.network.NetworkEvent
import com.libktx.game.network.NetworkEvent.EventType.GET
import com.libktx.game.network.NetworkEvent.EventType.POST
import com.libktx.game.network.NetworkEventListener
import com.libktx.game.network.PuzzleResponse
import com.libktx.game.network.ResponseHeaderKey
import io.undertow.Undertow
import io.undertow.server.HttpHandler
import io.undertow.server.HttpServerExchange
import io.undertow.server.handlers.BlockingHandler
import io.undertow.util.Headers
import io.undertow.util.HttpString
import io.undertow.util.Methods
import ktx.log.logger
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader


private val log = logger<Server>()

class Server(private val listener: NetworkEventListener) : HttpHandler {

    fun start() {
        val server = Undertow.builder()
                .addHttpListener(Config.ServerPort, "localhost")
                .setHandler(BlockingHandler(this)).build()
        server.start()
    }

    override fun handleRequest(exchange: HttpServerExchange) {
        var puzzleResponse = PuzzleResponse.FALSE

        if (Methods.POST == exchange.requestMethod) {
            puzzleResponse = listener.receivedNetworkEvent(NetworkEvent(endpointPath = exchange.relativePath, eventType = POST, data = exchange.getBody()))
        } else if (Methods.GET == exchange.requestMethod) {
            puzzleResponse = listener.receivedNetworkEvent(NetworkEvent(endpointPath = exchange.relativePath, eventType = GET))
        }
        log.info { "Puzzle Response: $puzzleResponse" }

        exchange.responseHeaders.put(Headers.CONTENT_TYPE, "text/plain; charset=utf-8")
        exchange.responseHeaders.put(HttpString(ResponseHeaderKey.BombKey.code), puzzleResponse.status.code)
        exchange.responseSender.send(puzzleResponse.data)
    }

    private fun HttpServerExchange.getBody(): String {
        var reader: BufferedReader? = null
        return try {
            this.startBlocking()
            reader = BufferedReader(InputStreamReader(this.inputStream))
            reader.readText()
        } catch (e: IOException) {
            log.error(e) { "Failed reading body from request: $reader" }
            ""
        } finally {
            reader?.close()
        }
    }
}