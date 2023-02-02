package com.ysshin.cpaquiz.domain.model

import com.ysshin.cpaquiz.core.base.DEFAULT_INT

data class WrongProblem(
    val year: Int = DEFAULT_INT,
    val pid: Int = DEFAULT_INT,
    val type: QuizType,
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
