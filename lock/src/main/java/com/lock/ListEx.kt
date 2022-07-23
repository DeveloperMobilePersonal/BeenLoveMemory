package com.lock

fun <T> MutableList<T>.result(): String {
    var pin = ""
    forEach { pin += it }
    return pin
}