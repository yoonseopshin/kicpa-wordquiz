package com.ysshin.cpaquiz.core.extension

fun <T> List<T>.extractItemsAroundTarget(item: T, itemCount: Int): List<T> {
    if (size <= itemCount) {
        return this
    }

    val startIndex = when (val targetIndex = indexOf(item)) {
        in 0..(itemCount / 2) -> 0
        in (size - (itemCount / 2)) until size -> size - itemCount
        else -> targetIndex - (itemCount / 2)
    }

    return subList(startIndex, startIndex + itemCount)
}
