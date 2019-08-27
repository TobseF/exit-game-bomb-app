package com.libktx.game.endpoint

import com.libktx.game.Game
import com.libktx.game.lib.Countdown
import com.libktx.game.network.AbstractNetworkEndpoint
import com.libktx.game.network.Endpoint
import com.libktx.game.network.PuzzleResponse

/**
 * Endpoint which stops the game with the command `BACK TO THE FUTURE`.
 *
 * Returns: `screen:LoginPuzzle;finish:1566910090308`
 *
 * Where `finish` is the time in ms, before the bomb explodes.
 */
class InfoEndpoint(val game: Game, val countdown: Countdown) : AbstractNetworkEndpoint(Endpoint.Info) {

    override fun request(data: String): PuzzleResponse {
        return if ("TELL ME SWEET LITTLE LIES" == data.toUpperCase()) {
            PuzzleResponse.OK("screen:${getCurrentScreen()};finish:${countdown.getFinishTime()}")
        } else {
            PuzzleResponse.FALSE
        }
    }

    private fun getCurrentScreen(): String {
        return game.shownScreen::class.simpleName!!
    }

}