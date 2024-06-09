package dev.sagar.playinfo.core.utils.coroutines

import kotlin.coroutines.CoroutineContext

interface CoroutineProvider {
    val main: CoroutineContext
    val io: CoroutineContext
}
