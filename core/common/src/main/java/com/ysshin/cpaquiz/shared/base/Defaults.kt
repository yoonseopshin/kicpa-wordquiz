package com.ysshin.cpaquiz.core.common

const val DEFAULT_INT = 0
const val DEFAULT_INVALID_INT = -1
const val DEFAULT_STRING = ""
val DEFAULT_STRING_LIST = emptyList<String>()

fun Int?.isNullOrDefault() = (this == null || this == DEFAULT_INT)
