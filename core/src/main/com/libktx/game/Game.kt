package com.libktx.game

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.libktx.game.lib.Countdown
import com.libktx.game.network.NetworkEvent
import com.libktx.game.network.NetworkEventListener
import com.libktx.game.network.PuzzleResponse
import com.libktx.game.puzzle.LoginPuzzle
import com.libktx.game.puzzle.NumbersPuzzle
import com.libktx.game.puzzle.NumbersPuzzleState
import com.libktx.game.puzzle.PuzzleManager
import com.libktx.game.screen.*
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.inject.Context
import ktx.log.logger

private val log = logger<Game>()

class Game : KtxGame<KtxScreen>(), NetworkEventListener {

    private val puzzleManager = PuzzleManager()

    override fun receivedNetworkEvent(event: NetworkEvent): PuzzleResponse {
        return puzzleManager.receivedNetworkEvent(event)
    }

    private val context = Context()

    override fun create() {
        context.register {
            bindSingleton(this@Game)
            bindSingleton<Batch>(SpriteBatch())
            bindSingleton(BitmapFont())
            bindSingleton(AssetManager())
            bindSingleton(ShapeRenderer())
            bindSingleton(Countdown(minutes = Config.countdownTime))

            bindSingleton(NumbersPuzzleState())

            // The camera ensures we can render using our target resolution of 800x480
            // pixels no matter what the screen resolution is.
            bindSingleton(OrthographicCamera().apply { setToOrtho(false, 800f, 480f) })
            bindSingleton(PooledEngine())

            addScreen(LoadingScreen(inject(), inject(), inject(), inject(), inject()))

            addPuzzle(LoginPuzzleScreen(inject(), inject(), inject(), inject(), inject(), inject()))
            addPuzzle(NumberPuzzleScreen(inject(), inject(), inject(), inject(), inject(), inject(), inject()))
            addPuzzle(EmptyPuzzleScreen(inject(), inject(), inject(), inject(), inject(), inject()))

            addScreen(ExplosionScreen(inject(), inject(), inject(), inject(), inject(), inject()))
            addScreen(SuccessScreen(inject(), inject(), inject(), inject(), inject(), inject()))

            puzzleManager.addPuzzle(LoginPuzzle())
            puzzleManager.addPuzzle(NumbersPuzzle(inject()))
        }
        setScreen<LoadingScreen>()
        super.create()
    }

    private inline fun <reified Type : AbstractPuzzleScreen> addPuzzle(puzzleScreen: Type) {
        puzzleManager.addPuzzleScreen(puzzleScreen)
        addScreen(puzzleScreen)
    }

    override fun dispose() {
        log.debug { "Entities in engine: ${context.inject<PooledEngine>().entities.size()}" }
        context.dispose()
        super.dispose()
    }
}
