package com.github.shatteredsoftware.core.util

fun <A, B> A?.nullMap(fn: (A) -> B): B? = if (this != null) fn(this) else null

fun <A> A?.nullForEach(fn: (A) -> Unit) { if (this != null) fn(this) }
