package com.libktx.game.desktop

import com.libktx.game.Config
import com.libktx.game.ecs.network.NetworkEvent
import com.libktx.game.ecs.network.NetworkEventListener
import com.libktx.game.ecs.network.ResponseStatus
import io.undertow.Undertow
import io.undertow.server.HttpHandler
import io.undertow.server.HttpServerExchange
import io.undertow.server.handlers.BlockingHandler
import io.undertow.util.Headers
import ktx.log.logger
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader


private val log = logger<Server>()

class Server(private val listener: () -> NetworkEventListener?) : HttpHandler {

    fun start() {
        val server = Undertow.builder()
                .addHttpListener(Config.ServerPort, "localhost")
                .setHandler(BlockingHandler(this)).build()
        server.start()
    }

    override fun handleRequest(exchange: HttpServerExchange) {
        listener.invoke()?.receivedNetworkEvent(NetworkEvent(endpoint = exchange.relativePath, data = exchange.getBody()))
        exchange.responseHeaders.put(Headers.CONTENT_TYPE, "text/plain; charset=utf-8")
        exchange.responseSender.send(ResponseStatus.OK.code)
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