package com.github.shatteredsoftware.core.task

import kotlinx.coroutines.*
import java.lang.Runnable
import java.time.Duration

class CoroutineExecutionStrategy: ExecutionStrategy, CoroutineScope {
    override val coroutineContext = Job()

    override fun execute(runnable: Runnable) {
        launch {
            runnable.run()
        }
    }

    override fun executeRepeating(interval: Duration, delay: Duration, runnable: Runnable) {
        val intervalMillis = interval.toMillis();
        val delayMillis = delay.toMillis();
        launch {
            delay(delayMillis)
            while(true) {
                runnable.run()
                delay(intervalMillis)
            }
        }
    }

    override fun executeDelayed(delay: Duration, runnable: Runnable) {
        val delayMillis = delay.toMillis();
        launch {
            delay(delayMillis)
            runnable.run()
        }
    }
}