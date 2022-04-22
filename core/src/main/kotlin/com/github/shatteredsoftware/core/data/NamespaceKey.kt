package com.github.shatteredsoftware.core.data

open class NamespaceKey(val namespace: String, val key: String) {
    override fun equals(other: Any?): Boolean {
        if (other is String) {
            return other.equals(this.toString(), ignoreCase = true)
        }
        if (other is NamespaceKey) {
            return other.namespace.equals(namespace, ignoreCase = true)
                && other.key.equals(key, ignoreCase = true)
        }
        return false
    }

    override fun toString(): String {
        return "$namespace:$key"
    }

    override fun hashCode(): Int {
        var result = namespace.hashCode()
        result = 31 * result + key.hashCode()
        return result
    }
}