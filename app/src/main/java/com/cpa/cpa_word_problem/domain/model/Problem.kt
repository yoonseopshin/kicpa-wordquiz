package com.cpa.cpa_word_problem.domain.model

data class Problem(
    val year: Int,
    val pid: Int,
    val description: String,
    val subDescriptions: List<String>,
    val questions: List<String>,
    val answer: Int,
    val type: Type
)