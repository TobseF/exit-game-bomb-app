package com.libktx.game

import com.koushikdutta.async.http.server.AsyncHttpServer
import com.koushikdutta.async.http.server.AsyncHttpServerRequest
import com.libktx.game.ecs.network.NetworkEvent
import com.libktx.game.ecs.network.NetworkEventListener
import com.libktx.game.ecs.network.ResponseHeaderKey
import ktx.log.logger

private val log = logger<Server>()

class Server(private val listener: () -> NetworkEventListener) {

    fun start() {
        val server = AsyncHttpServer()
        server.post(".*") { request, response ->
            log.info { "Received: $request" }

            val puzzleResponse = listener.invoke().receivedNetworkEvent(parseRequest(request))
            log.info { "Puzzle Response: $puzzleResponse" }

            response.headers.add(ResponseHeaderKey.BombKey.code, puzzleResponse.status.code)
            response.send(puzzleResponse.data)
        }

        server.listen(Config.ServerPort)
    }

    fun parseRequest(request: AsyncHttpServerRequest) = NetworkEvent(endpointPath = request.path, data = request.body.get().toString())

}