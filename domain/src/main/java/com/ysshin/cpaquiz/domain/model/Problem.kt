package com.ysshin.cpaquiz.domain.model

import com.ysshin.cpaquiz.shared.base.DEFAULT_INT
import com.ysshin.cpaquiz.shared.base.DEFAULT_STRING
import com.ysshin.cpaquiz.shared.base.DEFAULT_STRING_LIST

data class Problem(
    val year: Int = DEFAULT_INT,
    val pid: Int = DEFAULT_INT,
    val description: String = DEFAULT_STRING,
    val subDescriptions: List<String> = DEFAULT_STRING_LIST,
    val questions: List<String> = DEFAULT_STRING_LIST,
    val answer: Int = DEFAULT_INT,
    val type: QuizType = QuizType.Accounting,       // FIXME: Temporary default value
    val source: ProblemSource = ProblemSource.CPA,  // FIXME: Temporary default value
) {
    companion object {
        fun allYears() = (2016..2022).toList()
    }
}
