package com.libktx.game.endpoint

import com.libktx.game.Game
import com.libktx.game.Preferences
import com.libktx.game.lib.Countdown
import com.libktx.game.network.AbstractNetworkEndpoint
import com.libktx.game.network.Endpoint
import com.libktx.game.network.PuzzleResponse
import com.libktx.game.screen.BombState

/**
 * Endpoint which resets the game with the command `TURN IT OFF AND ON AGAIN;42`.
 * Where `42` is the time in minutes before the bomb explodes.
 */
class ResetEndpoint(val game: Game, private val bombState: BombState, val countdown: Countdown) : AbstractNetworkEndpoint(Endpoint.Reset) {

    fun reset(time: Int) {
        Preferences.countdownTime = time
        countdown.minutes = time
        countdown.reset()
        bombState.reset()
        game.reset()
    }

    override fun request(data: String): PuzzleResponse {
        return if (data.toUpperCase().startsWith("TURN IT OFF AND ON AGAIN")) {
            val time = data.substringAfter(";").toIntOrNull()
            if (time == null) {
                PuzzleResponse.FALSE
            } else {
                reset(time)
                PuzzleResponse.OK
            }
        } else {
            PuzzleResponse.FALSE
        }
    }

}