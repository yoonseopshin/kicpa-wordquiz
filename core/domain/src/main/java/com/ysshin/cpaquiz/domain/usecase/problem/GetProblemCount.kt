package com.ysshin.cpaquiz.domain.usecase.problem

import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.domain.repository.QuizRepository

class GetProblemCount(private val repository: QuizRepository) {

    operator fun invoke(type: QuizType) = repository.getProblemCountByType(type)
}
