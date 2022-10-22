package com.ysshin.cpaquiz.domain.usecase.problem

import com.ysshin.cpaquiz.domain.repository.QuizRepository

class DeleteAllWrongProblems(private val repository: QuizRepository) {

    suspend fun invoke() = repository.deleteAllWrongProblems()
}
