package com.libktx.game.lib

import ktx.inject.Context
import ktx.inject.InjectionException

class GameContext : Context() {

    /**
     * Provides optional instance of the selected type.
     * @return instance of the class with the selected type if a provider is present in the context.
     */
    inline fun <reified Type : Any> injectOptinal(): Type? {
        return try {
            getProvider(Type::class.java)()
        } catch (e: InjectionException) {
            null
        }
    }

    /**
     * Allows to register new components in the context with builder-like DSL
     * @param init will be invoked on this context.
     * @return this context.
     * @see bind
     * @see bindSingleton
     */
    inline fun bind(init: GameContext.() -> Unit): GameContext {
        this.init()
        return this
    }

}