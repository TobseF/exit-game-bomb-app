package com.libktx.game.assets

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.g2d.BitmapFont
import ktx.assets.getAsset
import ktx.assets.load

// sounds
enum class SoundAssets(val path: String) {
    MessageRight("sounds/correct_message.mp3"),
    MessageWrong("sounds/wrong_message.mp3"),
    BombActivated("sounds/bomb_activated.mp3"),
    BombDeactivated("sounds/bomb_deactivated.mp3"),
    BombExplosion("sounds/bomb_explosion.mp3")
}

fun AssetManager.load(asset: SoundAssets) = load<Sound>(asset.path)
operator fun AssetManager.get(asset: SoundAssets) = getAsset<Sound>(asset.path)


// fonts
enum class FontAssets(val path: String) {
    Counter("fonts/counter.fnt"),
    ConsolasBig("fonts/consolas_big.fnt"),
    Consolas("fonts/consolas_20.fnt"),
    NumbersBig("fonts/numbers_big.fnt")
}

fun AssetManager.load(asset: FontAssets) = load<BitmapFont>(asset.path)
operator fun AssetManager.get(asset: FontAssets) = getAsset<BitmapFont>(asset.path)