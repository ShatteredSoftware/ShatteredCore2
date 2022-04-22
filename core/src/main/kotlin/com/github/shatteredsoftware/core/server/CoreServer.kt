package com.github.shatteredsoftware.core.server

import arrow.core.None
import arrow.core.Option
import com.github.shatteredsoftware.core.data.NamespaceTypeKey
import com.github.shatteredsoftware.core.manager.CachedManager
import com.github.shatteredsoftware.core.manager.Manager
import com.github.shatteredsoftware.core.module.CoreModule

abstract class CoreServer {
    private val onLoadListeners: MutableList<() -> Unit> = mutableListOf()
    private val onDisableListeners: MutableList<() -> Unit> = mutableListOf()
    private val managers: MutableMap<NamespaceTypeKey<*>, Manager<*>> = mutableMapOf()
    private val modules: MutableMap<NamespaceTypeKey<out CoreModule>, CoreModule> = mutableMapOf()
    private var started = false;

    fun onLoad() {
        onLoadListeners.forEach { it() }
        for (module in modules.values) {
            module.onStart()
        }
        started = true;
    }

    fun onDisable() {
        onDisableListeners.forEach { it() }
        started = false;
    }

    fun doOnLoad(fn: () -> Unit) {
        onLoadListeners.add(fn)
    }

    fun doOnDisable(fn: () -> Unit) {
        onDisableListeners.add(fn)
    }

    fun removeModule(module: CoreModule) {
        module.onStop()
        val baseKey = module.getKey()
        val key = NamespaceTypeKey(baseKey.namespace, baseKey.key, module::class.java)
        val mod = modules[key] ?: return
        mod.getManagersToRegister().forEach {
            if (it is CachedManager) {
                it.flush()
            }
            this.managers.remove(it.key)
        }
        modules.remove(key)
    }

    fun addModule(module: CoreModule) {
        val baseKey = module.getKey()
        val key = NamespaceTypeKey(baseKey.namespace, baseKey.key, module::class.java)
        modules[key] = module
        managers.putAll(module.getManagersToRegister().map { it.key to it })
        if (started) {
            module.onStart()
        }
    }

    fun <T : CoreModule> getModule(key: NamespaceTypeKey<T>): Option<T> {
        val module = modules[key] ?: return None
        if (key.clazz.isInstance(module)) {
            @Suppress("UNCHECKED_CAST") // it matches the key and class, we're fine
            return Option.fromNullable(module as T)
        }
        return None
    }

    fun <T : Any> getManager(key: NamespaceTypeKey<T>): Option<Manager<T>> {
        val manager = managers[key] ?: return None
        if (key.clazz.isInstance(manager)) {
            @Suppress("UNCHECKED_CAST") // it matches the key and class, we're fine
            return Option.fromNullable(manager as Manager<T>)
        }
        return None
    }
}