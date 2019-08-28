package com.libktx.game.puzzle

import com.libktx.game.network.AbstractNetworkEndpoint
import com.libktx.game.network.Endpoint
import com.libktx.game.network.PuzzleResponse

/**
 * The player must provide the string `GOIN`
 */
class ConnectPuzzle : AbstractNetworkEndpoint(Endpoint.Connect) {

    override fun request(data: String): PuzzleResponse {
        return if ("GOIN" == data.toUpperCase()) {
            PuzzleResponse.OK
        } else {
            PuzzleResponse.FALSE
        }
    }

}