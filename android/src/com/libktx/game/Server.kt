package com.libktx.game

import com.koushikdutta.async.http.server.AsyncHttpServer
import com.koushikdutta.async.http.server.AsyncHttpServerRequest
import com.libktx.game.ecs.network.NetworkEvent
import com.libktx.game.ecs.network.NetworkEventListener
import com.libktx.game.ecs.network.ResponseStatus
import ktx.log.logger

private val log = logger<Server>()

class Server(private val listener: () -> NetworkEventListener?) {

    fun start() {
        val server = AsyncHttpServer()
        server.post(".*") { request, response ->
            log.info { "Received: $request" }
            listener.invoke()?.receivedNetworkEvent(parseRequest(request))
            response.send(ResponseStatus.OK.code)
        }

        server.listen(Config.ServerPort)
    }

    fun parseRequest(request: AsyncHttpServerRequest) = NetworkEvent(endpoint = request.path, data = request.body.get().toString())

}