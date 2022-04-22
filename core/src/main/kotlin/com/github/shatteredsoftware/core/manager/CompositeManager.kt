package com.github.shatteredsoftware.core.manager

import arrow.core.Either
import arrow.core.Option
import com.github.shatteredsoftware.core.data.NamespaceKey
import com.github.shatteredsoftware.core.data.NamespaceTypeKey
import com.github.shatteredsoftware.core.manager.read.ManagerReadError
import com.github.shatteredsoftware.core.manager.read.ManagerReads
import com.github.shatteredsoftware.core.manager.write.ManagerWriteError
import com.github.shatteredsoftware.core.manager.write.ManagerWrites

open class CompositeManager<T : Any>(
    override val key: NamespaceTypeKey<T>,
    private val reads: ManagerReads<out T>,
    private val writes: ManagerWrites<in T>
) :
    Manager<T> {
    override fun put(id: String, value: T): Option<ManagerWriteError> {
        return this.writes.put(id, value)
    }

    override fun get(id: String): Either<ManagerReadError, T> {
        return this.reads.get(id);
    }
}