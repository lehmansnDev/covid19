package de.simon.covid19.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable
import kotlin.coroutines.CoroutineContext

expect object MainLoopDispatcher : CoroutineDispatcher {
    override fun dispatch(context: CoroutineContext, block: Runnable)
}