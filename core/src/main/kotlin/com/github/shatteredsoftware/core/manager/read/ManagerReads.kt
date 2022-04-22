package com.github.shatteredsoftware.core.manager.read

import arrow.core.Either

interface ManagerReads<T> {
    fun get(id: String): Either<ManagerReadError, T>
}