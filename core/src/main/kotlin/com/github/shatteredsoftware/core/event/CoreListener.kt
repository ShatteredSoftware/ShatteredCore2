package com.github.shatteredsoftware.core.event

interface CoreListener<T : CoreEvent> {
    fun handle(event: T)
}