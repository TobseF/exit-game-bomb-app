package com.libktx.game.network

data class PuzzleResponse(val data: String = "", val status: ResponseStatus) {

    companion object {
        val OK = PuzzleResponse(data = ResponseStatus.OK.code, status = ResponseStatus.OK)
        val FALSE = PuzzleResponse(data = ResponseStatus.FALSE.code, status = ResponseStatus.FALSE)
    }
}