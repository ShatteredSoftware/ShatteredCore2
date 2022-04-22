package com.github.shatteredsoftware.core.services

import com.github.shatteredsoftware.core.server.CoreScheduler
import com.github.shatteredsoftware.core.server.CoreServer
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

class ServiceManager(@Inject val server: CoreServer, @Inject val scheduler: CoreScheduler) {
    private val services: MutableSet<CoreService> = mutableSetOf()
    private var initialized: Boolean = false

    init {
        server.doOnLoad { init() }
        server.doOnDisable { shutdown() }
    }

    fun registerService(service: CoreService) {
        services.add(service)
        if (initialized) {
            service.init()
        }
    }

    fun init() {
        if (initialized) return
        initialized = true
        services.forEach{ it.init(); scheduler.interval(it.key, it.savePeriod.seconds) { it.autosave() } }
    }

    fun reload() {
        services.forEach(CoreService::reload)
    }

    fun shutdown() {
        initialized = false
        services.forEach { it.shutdown(); scheduler.cancel(it.key) }
    }
}