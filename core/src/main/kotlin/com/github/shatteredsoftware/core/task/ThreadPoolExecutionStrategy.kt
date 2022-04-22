package com.github.shatteredsoftware.core.task

import java.time.Duration
import java.util.concurrent.ThreadPoolExecutor

class ThreadPoolExecutionStrategy(val threadPool: ThreadPoolExecutor): ExecutionStrategy {
    override fun execute(runnable: Runnable) {
        threadPool.execute(runnable);
    }

    override fun executeRepeating(interval: Duration, delay: Duration, runnable: Runnable) {
        val intervalMillis = interval.toMillis();
        val delayMillis = delay.toMillis();
        threadPool.execute() {
            Thread.sleep(delayMillis)
            while (true) {
                runnable.run()
                Thread.sleep(intervalMillis);
            }
        }
    }

    override fun executeDelayed(delay: Duration, runnable: Runnable) {
        val delayMillis = delay.toMillis();
        threadPool.execute() {
            Thread.sleep(delayMillis)
            runnable.run()
        }
    }
}