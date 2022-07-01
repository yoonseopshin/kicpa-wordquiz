package com.ysshin.cpaquiz.domain.usecase.problem

import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.domain.repository.QuizRepository

class DeleteWrongProblem(private val repository: QuizRepository) {

    suspend operator fun invoke(year: Int, pid: Int, type: QuizType) = repository.deleteWrongProblem(year, pid, type)
}
