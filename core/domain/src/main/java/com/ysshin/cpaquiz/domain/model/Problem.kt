package com.ysshin.cpaquiz.domain.model

import com.ysshin.cpaquiz.core.common.DEFAULT_INT
import com.ysshin.cpaquiz.core.common.DEFAULT_STRING
import com.ysshin.cpaquiz.core.common.DEFAULT_STRING_LIST

data class Problem(
    val year: Int = DEFAULT_INT,
    val pid: Int = DEFAULT_INT,
    val description: String = DEFAULT_STRING,
    val subDescriptions: List<String> = DEFAULT_STRING_LIST,
    val questions: List<String> = DEFAULT_STRING_LIST,
    val answer: Int = DEFAULT_INT,
    val type: QuizType = QuizType.Accounting, // FIXME: Temporary default value
    val source: ProblemSource = ProblemSource.CPA, // FIXME: Temporary default value
) {
    companion object {
        fun allYears() = (2016..2022).toList()
    }
}

fun Problem.isValid() = year > 0 && pid > 0
