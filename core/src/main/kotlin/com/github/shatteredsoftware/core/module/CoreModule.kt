package com.github.shatteredsoftware.core.module

import arrow.core.None
import arrow.core.Option
import com.github.shatteredsoftware.core.data.NamespaceKey
import com.github.shatteredsoftware.core.manager.Manager

interface CoreModule {
    fun getKey(): NamespaceKey

    /**
     * Allows you to overload another module with the functionality of this module.
     */
    fun getOverloadKey(): Option<NamespaceKey> = None

    fun getManagersToRegister(): List<Manager<*>> {
        return emptyList()
    }

    fun onStart(): () -> Unit
    fun onStop(): () -> Unit
}