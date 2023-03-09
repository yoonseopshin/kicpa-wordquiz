package com.ysshin.cpaquiz.data.network.response

import com.ysshin.cpaquiz.domain.model.ProblemSource
import com.ysshin.cpaquiz.domain.model.QuizType
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
    val source: ProblemSource,
    val subtype: String,
)
