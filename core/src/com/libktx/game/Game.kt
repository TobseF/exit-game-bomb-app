package com.libktx.game

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.libktx.game.ecs.network.NetworkEvent
import com.libktx.game.ecs.network.NetworkEventListener
import com.libktx.game.ecs.network.PuzzleResponse
import com.libktx.game.lib.Countdown
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

    val puzzleManager = PuzzleManager()

    override fun receivedNetworkEvent(event: NetworkEvent): PuzzleResponse {
        puzzleManager.receivedNetworkEvent(event)
        return PuzzleResponse.OK
    }

    private val context = Context()

    private var networkEventListener: NetworkEventListener? = null

    override fun create() {
        context.register {
            bindSingleton(this@Game)
            bindSingleton<Batch>(SpriteBatch())
            bindSingleton(BitmapFont())
            bindSingleton(AssetManager())
            bindSingleton(ShapeRenderer())
            bindSingleton(Countdown(minutes = 20))

            bindSingleton(NumbersPuzzleState())

            // The camera ensures we can render using our target resolution of 800x480
            // pixels no matter what the screen resolution is.
            bindSingleton(OrthographicCamera().apply { setToOrtho(false, 800f, 480f) })
            bindSingleton(PooledEngine())

            addScreen(LoadingScreenScreen(inject(), inject(), inject(), inject(), inject()))

            addPuzzle(LoginPuzzleScreen(inject(), inject(), inject(), inject(), inject(), inject()))
            addPuzzle(NumberPuzzleScreen(inject(), inject(), inject(), inject(), inject(), inject(), inject()))
            addPuzzle(EmptyPuzzleScreen(inject(), inject(), inject(), inject(), inject(), inject()))

            networkEventListener = null
            addScreen(SampleGameScreen(inject(), inject(), inject(), inject(), inject()))

            puzzleManager.addPuzzle(LoginPuzzle())
            puzzleManager.addPuzzle(NumbersPuzzle(inject()))
        }
        setScreen<LoadingScreenScreen>()
        super.create()
    }

    private inline fun <reified Type : AbstractPuzzleScreen> addPuzzle(puzzleScreen: Type) {
        puzzleManager.addPuzzleScreen(puzzleScreen)
        addScreen(puzzleScreen)
    }

    fun getNetworkEventListener() = puzzleManager

    override fun dispose() {
        log.debug { "Entities in engine: ${context.inject<PooledEngine>().entities.size()}" }
        context.dispose()
        super.dispose()
    }
}
