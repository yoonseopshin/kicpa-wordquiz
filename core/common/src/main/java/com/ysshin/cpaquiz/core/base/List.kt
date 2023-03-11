package com.ysshin.cpaquiz.core.base

operator fun <T> List<T>.times(n: Int): List<T> {
    return List(n) { this }.flatten()
}
