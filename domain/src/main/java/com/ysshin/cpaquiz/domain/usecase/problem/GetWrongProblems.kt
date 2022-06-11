package com.ysshin.cpaquiz.domain.usecase.problem

import com.ysshin.cpaquiz.domain.repository.QuizRepository
import kotlinx.coroutines.flow.map

class GetWrongProblems(private val repository: QuizRepository) {

    operator fun invoke() = repository.getWrongProblems().map { it.reversed() }
}
