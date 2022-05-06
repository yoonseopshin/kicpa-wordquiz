package com.cpa.cpa_word_problem.feature.quiz.domain.model

import com.cpa.cpa_word_problem.util.DEFAULT_INT

data class WrongProblem(
    val year: Int = DEFAULT_INT,
    val pid: Int = DEFAULT_INT,
    val type: QuizType = QuizType.None,
    val createdAt: Long = System.currentTimeMillis()
) {
    companion object {
        fun from(problem: Problem) = WrongProblem(
            year = problem.year,
            pid = problem.pid,
            type = problem.type
        )
    }
}
