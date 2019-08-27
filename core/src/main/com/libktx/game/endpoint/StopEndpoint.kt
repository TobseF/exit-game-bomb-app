package com.libktx.game.endpoint

import com.libktx.game.Game
import com.libktx.game.lib.Countdown
import com.libktx.game.network.AbstractNetworkEndpoint
import com.libktx.game.network.Endpoint
import com.libktx.game.network.PuzzleResponse

/**
 * Endpoint which stops the game with the command `BACK TO THE FUTURE`
 */
class StopEndpoint(val game: Game, val countdown: Countdown) : AbstractNetworkEndpoint(Endpoint.Stop) {


    override fun request(data: String): PuzzleResponse {
        return if ("BACK TO THE FUTURE" == data.toUpperCase()) {
            game.stop()
            countdown.stop()
            PuzzleResponse.OK
        } else {
            PuzzleResponse.FALSE
        }
    }

}