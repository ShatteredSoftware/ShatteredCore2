package com.github.shatteredsoftware.core.task

import java.time.Duration

interface ExecutionStrategy {
    fun execute(runnable: Runnable)
    fun executeRepeating(interval: Duration, delay: Duration, runnable: Runnable)
    fun executeDelayed(delay: Duration, runnable: Runnable)
}