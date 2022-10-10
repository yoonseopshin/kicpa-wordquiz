package com.ysshin.cpaquiz.feature.quiz.presentation.model

import android.os.Parcelable
import com.ysshin.cpaquiz.domain.model.ProblemSource
import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.core.common.DEFAULT_INT
import com.ysshin.cpaquiz.core.common.DEFAULT_STRING
import com.ysshin.cpaquiz.core.common.DEFAULT_STRING_LIST
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProblemModel(
    val year: Int = DEFAULT_INT,
    val pid: Int = DEFAULT_INT,
    val description: String = DEFAULT_STRING,
    val subDescriptions: List<String> = DEFAULT_STRING_LIST,
    val questions: List<String> = DEFAULT_STRING_LIST,
    val answer: Int = DEFAULT_INT,
    val type: QuizType,
    val source: ProblemSource,
) : Parcelable
