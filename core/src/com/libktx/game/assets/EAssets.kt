package com.libktx.game.assets

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import ktx.assets.getAsset
import ktx.assets.load

// sounds
enum class SoundAssets(val path: String) {
    Drop("sounds/drop.wav")
}

fun AssetManager.load(asset: SoundAssets) = load<Sound>(asset.path)
operator fun AssetManager.get(asset: SoundAssets) = getAsset<Sound>(asset.path)


// fonts
enum class FontAssets(val path: String) {
    Counter("fonts/counter.fnt"),
    ConsolasBig("fonts/consolas_big.fnt")
}

fun AssetManager.load(asset: FontAssets) = load<BitmapFont>(asset.path)
operator fun AssetManager.get(asset: FontAssets) = getAsset<BitmapFont>(asset.path)


// music
enum class MusicAssets(val path: String) {
    Rain("music/rain.mp3")
}

fun AssetManager.load(asset: MusicAssets) = load<Music>(asset.path)
operator fun AssetManager.get(asset: MusicAssets) = getAsset<Music>(asset.path)

// texture atlas
enum class TextureAtlasAssets(val path: String) {
    Game("images/game.atlas")
}

fun AssetManager.load(asset: TextureAtlasAssets) = load<TextureAtlas>(asset.path)
operator fun AssetManager.get(asset: TextureAtlasAssets) = getAsset<TextureAtlas>(asset.path)