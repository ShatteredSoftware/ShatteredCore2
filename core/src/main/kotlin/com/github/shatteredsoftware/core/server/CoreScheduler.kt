package com.github.shatteredsoftware.core.server

import com.github.shatteredsoftware.core.data.NamespaceKey
import kotlin.time.Duration

interface CoreScheduler {
    fun after(key: NamespaceKey, duration: Duration, fn: () -> Unit)
    fun interval(key: NamespaceKey, duration: Duration, fn: () -> Unit)
    fun cancel(key: NamespaceKey)
}