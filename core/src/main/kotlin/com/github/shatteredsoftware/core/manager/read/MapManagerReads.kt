package com.github.shatteredsoftware.core.manager.read

import arrow.core.Either
import arrow.core.Option
import arrow.core.getOrElse
import com.github.shatteredsoftware.core.util.nullMap

class MapManagerReads<T>(private val underlying: Map<String, T> = mutableMapOf()) : ManagerReads<T> {
    override fun get(id: String): Either<ManagerReadError, T> {
        return underlying[id].nullMap { Either.Right(it) } ?: Either.Left(
            ManagerReadError(
                "Could not get '$id' from internal map",
                ManagerReadError.ReadErrorType.NotFound
            )
        )
    }
}