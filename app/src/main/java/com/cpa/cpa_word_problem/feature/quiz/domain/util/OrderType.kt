package com.cpa.cpa_word_problem.feature.quiz.domain.util

sealed class OrderType {
    object Ascending : OrderType()
    object Descending : OrderType()
}
