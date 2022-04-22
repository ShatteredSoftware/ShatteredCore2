package com.github.shatteredsoftware.core.manager

import arrow.core.Either
import arrow.core.Option
import com.github.shatteredsoftware.core.data.NamespaceKey
import com.github.shatteredsoftware.core.data.NamespaceTypeKey
import com.github.shatteredsoftware.core.manager.read.ManagerReadError
import com.github.shatteredsoftware.core.manager.read.ManagerReads
import com.github.shatteredsoftware.core.manager.write.ManagerWriteError
import com.github.shatteredsoftware.core.manager.write.ManagerWrites

interface Manager<T : Any> {
    companion object {
        fun <T : Any> builder(key: NamespaceTypeKey<T>, reads: ManagerReads<out T>, writes: ManagerWrites<in T>) =
            ManagerBuilder(key, reads, writes)
    }

    val key: NamespaceTypeKey<out T>
    fun put(id: String, value: T): Option<ManagerWriteError>
    fun get(id: String): Either<ManagerReadError, T>
}