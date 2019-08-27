package com.libktx.game.network

data class PuzzleResponse(val data: String = "", val status: ResponseStatus) {

    companion object {
        fun OK(message: String) = PuzzleResponse(data = message, status = ResponseStatus.OK)

        val OK = PuzzleResponse(data = ResponseStatus.OK.code, status = ResponseStatus.OK)
        val FALSE = PuzzleResponse(data = ResponseStatus.FALSE.code, status = ResponseStatus.FALSE)
    }
}