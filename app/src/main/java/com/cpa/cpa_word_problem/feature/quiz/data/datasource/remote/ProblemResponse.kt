package com.cpa.cpa_word_problem.feature.quiz.data.datasource.remote

import com.cpa.cpa_word_problem.feature.quiz.domain.model.QuizType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProblemResponse(
    val year: Int,
    val pid: Int,
    val description: String,
    @SerialName("sub_description")
    val subDescriptions: List<String>,
    val questions: List<String>,
    val answer: Int,
    val type: QuizType,
)
