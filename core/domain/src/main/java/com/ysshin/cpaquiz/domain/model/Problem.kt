package com.ysshin.cpaquiz.domain.model

import com.ysshin.cpaquiz.core.base.DEFAULT_INT
import com.ysshin.cpaquiz.core.base.DEFAULT_STRING
import com.ysshin.cpaquiz.core.base.DEFAULT_STRING_LIST

data class Problem(
    val year: Int,
    val pid: Int,
    val description: String = DEFAULT_STRING,
    val subDescriptions: List<String> = DEFAULT_STRING_LIST,
    val questions: List<String> = DEFAULT_STRING_LIST,
    val answer: Int = DEFAULT_INT,
    val type: QuizType,
    val source: ProblemSource,
    val subtype: String = DEFAULT_STRING,
) {
    companion object {
        fun default() = Problem(year = 0, pid = 0, type = QuizType.Accounting, source = ProblemSource.CPA)
    }
}

fun Problem.isValid() = year > 0 && pid > 0
