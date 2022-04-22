package com.github.shatteredsoftware.core.manager

import com.github.shatteredsoftware.core.data.NamespaceTypeKey
import com.github.shatteredsoftware.core.manager.read.ManagerReads
import com.github.shatteredsoftware.core.manager.write.ManagerWrites
import com.github.shatteredsoftware.core.task.ExecutionStrategy
import java.time.Duration

class ManagerBuilder<T : Any>(key: NamespaceTypeKey<T>, reads: ManagerReads<out T>, writes: ManagerWrites<in T>) {
    var underlying: Manager<T> = CompositeManager(key, reads, writes);
    var disposed: Boolean = false;

    fun withCache(strategy: CachedManager.PushStrategy = CachedManager.PushStrategy.Instant) {
        checkDisposed()
        underlying = CachedManager(underlying, strategy);
    }

    fun withLifetime(lifetime: Duration) {
        checkDisposed()
        val currentUnderlying = underlying;
        underlying =
            if (currentUnderlying is CachedManager) {
                LifetimeCachedManager(currentUnderlying, lifetime)
            } else {
                LifetimeCachedManager(CachedManager(currentUnderlying), lifetime)
            }
    }

    fun async(executionStrategy: ExecutionStrategy) {
        checkDisposed()
        underlying = AsyncManager(underlying, executionStrategy)
    }

    fun build(): Manager<T> {
        checkDisposed()
        this.disposed = true;
        return this.underlying
    }

    private fun checkDisposed() {
        if (this.disposed) {
            throw Exception("Trying to operate on a disposed ManagerBuilder. Please create a new builder.")
        }
    }
}