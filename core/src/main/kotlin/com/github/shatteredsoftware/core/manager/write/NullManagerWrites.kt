package com.github.shatteredsoftware.core.manager.write

import arrow.core.Some

object NullManagerWrites : ManagerWrites<Any> {
    override fun put(id: String, value: Any): Some<ManagerWriteError> {
        return Some(
            ManagerWriteError(
                "Trying to write '$id'='$value' to a non-writeable manager",
                ManagerWriteError.ReadErrorType.InvalidError
            )
        )
    }
}