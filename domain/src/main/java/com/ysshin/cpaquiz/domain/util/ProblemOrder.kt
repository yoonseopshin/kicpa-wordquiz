package com.ysshin.cpaquiz.domain.util

import com.ysshin.cpaquiz.domain.model.Problem

sealed class ProblemOrder(private val orderType: OrderType) {

    class Time(orderType: OrderType) : ProblemOrder(orderType)

    operator fun invoke(problems: List<Problem>) = when (orderType) {
        is OrderType.Ascending -> {
            when (this) {
                is Time -> problems.sortedWith(compareBy({ it.type }, { it.year }, { it.pid }))
            }
        }
        is OrderType.Descending -> {
            when (this) {
                is Time -> problems.sortedWith(compareBy({ it.year }, { it.pid }, { it.type }))
                    .asReversed()
            }
        }
    }

}
