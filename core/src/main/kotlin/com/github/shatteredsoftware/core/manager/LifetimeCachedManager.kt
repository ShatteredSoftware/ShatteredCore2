package com.github.shatteredsoftware.core.manager

import arrow.core.Either
import arrow.core.Option
import com.github.shatteredsoftware.core.manager.read.ManagerReadError
import com.github.shatteredsoftware.core.manager.write.ManagerWriteError
import java.time.Duration
import java.time.Instant

class LifetimeCachedManager<T : Any>(private val wrapped: CachedManager<T>, private val lifetime: Duration): Manager<T> by wrapped {
    private val lastAccess = mutableMapOf<String, Instant>();

    @Synchronized
    override fun put(id: String, value: T): Option<ManagerWriteError> {
        lastAccess[id] = Instant.now()
        return wrapped.put(id, value)
    }

    @Synchronized
    override fun get(id: String): Either<ManagerReadError, T> {
        val now = Instant.now()
        val last = lastAccess[id] ?: Instant.EPOCH
        lastAccess[id] = now
        if (now.isAfter(last + lifetime)) {
            wrapped.invalidate(id)
        }
        return wrapped.get(id);
    }
}