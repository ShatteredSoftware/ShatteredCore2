package com.github.shatteredsoftware.core.task

import java.time.Duration

/**
 * This ignores your requests to do things later. I would not use it anywhere but testing.
 */
class SynchronousExecutionStrategy : ExecutionStrategy {
    override fun execute(runnable: Runnable) {
        runnable.run()
    }

    override fun executeRepeating(interval: Duration, delay: Duration, runnable: Runnable) {
        runnable.run()
    }

    override fun executeDelayed(delay: Duration, runnable: Runnable) {
        runnable.run()
    }
}