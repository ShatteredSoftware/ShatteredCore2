package com.github.shatteredsoftware.core.manager.read

import arrow.core.Either

object NullManagerReads : ManagerReads<Nothing> {
    override fun get(id: String): Either.Left<ManagerReadError> {
        return Either.Left(
            ManagerReadError(
                "Trying to read '$id' from a non-readable manager",
                ManagerReadError.ReadErrorType.NotFound
            )
        )
    }
}