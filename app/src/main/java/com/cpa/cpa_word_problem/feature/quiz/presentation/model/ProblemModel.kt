package com.cpa.cpa_word_problem.feature.quiz.presentation.model

import android.os.Parcelable
import com.ysshin.cpaquiz.domain.model.ProblemSource
import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.shared.base.DEFAULT_INT
import com.ysshin.cpaquiz.shared.base.DEFAULT_STRING
import com.ysshin.cpaquiz.shared.base.DEFAULT_STRING_LIST
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProblemModel(
    val year: Int = DEFAULT_INT,
    val pid: Int = DEFAULT_INT,
    val description: String = DEFAULT_STRING,
    val subDescriptions: List<String> = DEFAULT_STRING_LIST,
    val questions: List<String> = DEFAULT_STRING_LIST,
    val answer: Int = DEFAULT_INT,
    val type: QuizType = QuizType.None,
    val source: ProblemSource = ProblemSource.None,
) : Parcelable
