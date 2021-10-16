package de.simon.covid19.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable
import kotlin.coroutines.CoroutineContext

actual object MainLoopDispatcher : CoroutineDispatcher() {
    actual override fun dispatch(context: CoroutineContext, block: Runnable) {
        context.run {
            block
        }
    }
}