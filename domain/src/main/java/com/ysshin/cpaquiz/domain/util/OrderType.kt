package com.ysshin.cpaquiz.domain.util

sealed class OrderType {
    object Ascending : OrderType()
    object Descending : OrderType()
}
