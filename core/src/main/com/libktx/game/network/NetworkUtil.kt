package com.libktx.game.network

import java.net.DatagramSocket
import java.net.InetAddress

object Network {

    fun getIpAddress(): String {
        DatagramSocket().use { socket ->
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002)
            return socket.localAddress.hostAddress
        }
    }
}