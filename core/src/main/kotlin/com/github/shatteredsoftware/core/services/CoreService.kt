package com.github.shatteredsoftware.core.services

import com.github.shatteredsoftware.core.data.NamespaceKey
import com.github.shatteredsoftware.core.server.CoreServer

interface CoreService {
    /** Number of seconds between autosave requests */
    val savePeriod: Int
    val key: NamespaceKey

    /** */
    fun init()
    fun shutdown()
    fun autosave() { }
    fun reload() {
        shutdown()
        reload()
    }
}