package com.github.shatteredsoftware.core.plugin

import com.github.shatteredsoftware.core.data.NamespaceKey

class CorePlugin(val name: String) {
    fun key(key: String): NamespaceKey {
        return NamespaceKey(name, name)
    }
}