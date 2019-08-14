package com.libktx.game

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.libktx.game.ecs.network.NetworkEventListener
import com.libktx.game.lib.Countdown
import com.libktx.game.screen.*
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.inject.Context
import ktx.log.logger

private val log = logger<Game>()

class Game : KtxGame<KtxScreen>() {
    private val context = Context()

    private var networkEventListener : NetworkEventListener? = null

    override fun create() {
        context.register {
            bindSingleton(this@Game)
            bindSingleton<Batch>(SpriteBatch())
            bindSingleton(BitmapFont())
            bindSingleton(AssetManager())
            bindSingleton(ShapeRenderer())
            bindSingleton(Countdown(minutes = 20))
            // The camera ensures we can render using our target resolution of 800x480
            //    pixels no matter what the screen resolution is.
            bindSingleton(OrthographicCamera().apply { setToOrtho(false, 800f, 480f) })
            bindSingleton(PooledEngine())

            addScreen(LoadingScreenScreen(inject(), inject(), inject(), inject(), inject()))
            addScreen(LoginPuzzleScreen(inject(), inject(), inject(), inject(), inject(), inject()))
            addScreen(NumberPuzzleScreen(inject(), inject(), inject(), inject(), inject(), inject()))
            addScreen(EmptyPuzzleScreen(inject(), inject(), inject(), inject(), inject(), inject()))

            val gameScreen = SampleGameScreen(inject(), inject(), inject(), inject(), inject())
            networkEventListener = gameScreen
            addScreen(gameScreen)
        }
        setScreen<LoadingScreenScreen>()
        super.create()
    }

    fun getNetworkEventListener() = networkEventListener

    override fun dispose() {
        log.debug { "Entities in engine: ${context.inject<PooledEngine>().entities.size()}" }
        context.dispose()
        super.dispose()
    }
}
