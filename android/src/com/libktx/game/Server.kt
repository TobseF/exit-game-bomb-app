package com.libktx.game

import com.koushikdutta.async.http.server.AsyncHttpServer
import com.koushikdutta.async.http.server.AsyncHttpServerRequest
import com.libktx.game.network.NetworkEvent
import com.libktx.game.network.NetworkEvent.EventType.GET
import com.libktx.game.network.NetworkEvent.EventType.POST
import com.libktx.game.network.NetworkEventListener
import com.libktx.game.network.ResponseHeaderKey
import ktx.log.logger

private val log = logger<Server>()

class Server(private val listener: NetworkEventListener) {

    fun start() {
        val server = AsyncHttpServer()
        server.post(".*") { request, response ->
            log.info { "Received post: $request" }

            val puzzleResponse = listener.receivedNetworkEvent(parseRequest(POST, request))
            log.info { "Puzzle Response: $puzzleResponse" }

            response.headers.add(ResponseHeaderKey.BombKey.code, puzzleResponse.status.code)
            response.send(puzzleResponse.data)
        }

        server.get(".*") { request, response ->
            log.info { "Received get: $request" }

            val puzzleResponse = listener.receivedNetworkEvent(parseRequest(GET, request))
            log.info { "Puzzle Response: $puzzleResponse" }

            response.headers.add(ResponseHeaderKey.BombKey.code, puzzleResponse.status.code)
            response.send(puzzleResponse.data)
        }

        server.listen(Config.serverPort)
    }

    private fun parseRequest(eventType: NetworkEvent.EventType, request: AsyncHttpServerRequest): NetworkEvent {
        val body = request.body.get()
        return if (body == null) {
            log.info { "Request $eventType without body: $request.path" }
            NetworkEvent(endpointPath = request.path, eventType = eventType)
        } else {
            NetworkEvent(endpointPath = request.path, eventType = eventType, data = body.toString())
        }
    }
}