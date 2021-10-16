package de.simon.covid19.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable
import platform.Foundation.NSRunLoop
import platform.Foundation.performBlock
import kotlin.coroutines.CoroutineContext

actual object MainLoopDispatcher: CoroutineDispatcher() {
    actual override fun dispatch(context: CoroutineContext, block: Runnable) {
        NSRunLoop.mainRunLoop().performBlock {
            block.run()
        }
    }
}