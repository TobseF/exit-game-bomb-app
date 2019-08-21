package com.libktx.game.network

import java.net.DatagramSocket
import java.net.InetAddress

object Network {

    val ipAddress by lazy { resolveIpAddress() }

    /**
     * Returns the IP address without establishing a real connection.
     */
    private fun resolveIpAddress(): String {
        DatagramSocket().use { socket ->
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002)
            return socket.localAddress.hostAddress
        }
    }
}
