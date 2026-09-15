package com.narinc.posts.core

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Bağımlılığı azaltmak için common Provider
 * testlerde TestDispatcherProvider ile değiştirilir.
 *
 */
interface DispatcherProvider {
    val io: CoroutineDispatcher
    val main: CoroutineDispatcher
    val default: CoroutineDispatcher
}