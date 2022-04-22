package com.github.shatteredsoftware.core.manager

import arrow.core.Either
import arrow.core.None
import arrow.core.Option
import com.github.shatteredsoftware.core.manager.read.ManagerReadError
import com.github.shatteredsoftware.core.manager.write.ManagerWriteError


open class CachedManager<T : Any>(
    private val wrapped: Manager<T>,
    private val pushStrategy: PushStrategy = PushStrategy.Instant
) : Manager<T> by wrapped {
    enum class PushStrategy {
        Manual,
        Instant
    }

    private val cache = mutableMapOf<String, T>();

    open fun flush(): List<ManagerWriteError> {
        return cache.entries.mapNotNull { (id, value) ->
            this.wrapped.put(id, value).orNull()
        };
    }

    @Synchronized
    fun invalidate() {
        this.cache.clear();
    }

    @Synchronized
    fun invalidate(id: String) {
        this.cache.remove(id);
    }

    @Synchronized
    override fun put(id: String, value: T): Option<ManagerWriteError> {
        this.cache.put(id, value);
        if (this.pushStrategy == PushStrategy.Instant) {
            return this.wrapped.put(id, value);
        }
        return None;
    }

    @Synchronized
    override fun get(id: String): Either<ManagerReadError, T> {
        val possible = this.cache[id];
        if (possible != null) {
            return Either.Right(possible)
        }
        val nonCached = this.wrapped.get(id);
        return when (nonCached) {
            is Either.Left -> nonCached
            is Either.Right -> {
                cache[id] = nonCached.value
                nonCached
            }
        }
    }
}