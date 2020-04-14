package com.example.devlibrary.utils

/**
 * @author FQH
 * Create at 2020/4/11.
 */

fun <T> Any?.notNull(f: () -> T, t: () -> T): T {
    return if (this != null) f() else t()
}

fun <T> Any?.notNull(f: () -> Unit, t: () -> Unit) {
    if (this != null) f() else t()
}

fun <T> Any?.isNull(f: () -> Unit) {
    if (this == null) f()
}