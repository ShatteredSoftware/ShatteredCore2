package com.github.shatteredsoftware.core.manager.write

import arrow.core.Option

interface ManagerWrites<T> {
    fun put(id: String, value: T): Option<ManagerWriteError>
}