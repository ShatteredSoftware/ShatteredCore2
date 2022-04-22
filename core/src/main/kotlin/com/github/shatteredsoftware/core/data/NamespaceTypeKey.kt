package com.github.shatteredsoftware.core.data

class NamespaceTypeKey<T>(namespace: String, key: String, val clazz: Class<T>) : NamespaceKey(namespace, key) {
    override fun equals(other: Any?): Boolean {
        if (other !is NamespaceTypeKey<*>) {
            return false
        }
        return namespace == other.namespace && key == other.key && clazz == other.clazz
    }
}