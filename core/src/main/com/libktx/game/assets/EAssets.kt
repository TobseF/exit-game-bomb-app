package com.libktx.game.assets

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.g2d.BitmapFont
import ktx.assets.Asset
import ktx.assets.load

// sounds
enum class SoundAssets(override val file: String) : AssetReference {
    MessageRight("correct_message.mp3"),
    MessageWrong("wrong_message.mp3"),
    BombDeactivated("bomb_deactivated.mp3"),
    BombExplosion("bomb_explosion.mp3");

    override val folder = "sounds"
}

fun AssetManager.load(asset: SoundAssets) = load<Sound>(asset)
operator fun AssetManager.get(asset: SoundAssets) = getAsset<Sound>(asset)


// music
enum class MusicAssets(override val file: String) : AssetReference {
    /**
     * This clips is longer than 5s, so we have to stream it as music.
     */
    BombActivated("bomb_activated_voice.mp3");

    override val folder = "sounds"
}

fun AssetManager.load(asset: MusicAssets) = load<Music>(asset)
operator fun AssetManager.get(asset: MusicAssets) = getAsset<Music>(asset)

// fonts
enum class FontAssets(override val file: String) : AssetReference {
    Counter("counter.fnt"),
    CounterBig("counter_big.fnt"),
    ConsolasBig("consolas_big.fnt"),
    Consolas("consolas_20.fnt"),
    NumbersBig("numbers_big.fnt");

    override val folder = "fonts"
}

fun AssetManager.load(asset: FontAssets) = load<BitmapFont>(asset)
operator fun AssetManager.get(asset: FontAssets) = getAsset<BitmapFont>(asset)

// app icons
enum class Icons(override val file: String) : AssetReference {
    AppIcon64("icon_64.png"),
    AppIcon16("icon_16.png");

    override val folder = "icons"
}

interface AssetReference {
    val file: String
    val folder: String
    val path: String
        get() = "$folder/$file"
}

inline fun <reified Type : Any> AssetManager.getAsset(reference: AssetReference): Type = this[reference.path]
inline fun <reified Type : Any> AssetManager.load(reference: AssetReference): Asset<Type> = load(reference.path)