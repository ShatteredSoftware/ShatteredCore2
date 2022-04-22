package com.github.shatteredsoftware.core.manager

import com.github.shatteredsoftware.core.manager.write.ManagerWriteError
import com.github.shatteredsoftware.core.task.ExecutionStrategy

class AsyncManager<T : Any>(private val wrapped: Manager<T>, val executionStrategy: ExecutionStrategy) :
    CachedManager<T>(wrapped, PushStrategy.Manual) {

    var lastErrors: List<ManagerWriteError> = emptyList()
        private set;

    /**
     * Flushes all cached items to the backing writes using the given run strategy. Does not return errors, but instead
     * stores them in the [`lastErrors`](AsyncManager#lastErrors) field.
     */
    override fun flush(): List<ManagerWriteError> {
        executionStrategy.execute {
            this@AsyncManager.lastErrors = super.flush();
        }
        return emptyList();
    }
}