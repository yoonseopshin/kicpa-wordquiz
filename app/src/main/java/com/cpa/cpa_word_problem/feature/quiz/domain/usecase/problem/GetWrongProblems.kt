package com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem

import com.cpa.cpa_word_problem.feature.quiz.domain.model.Problem
import com.cpa.cpa_word_problem.feature.quiz.domain.repository.QuizRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetWrongProblems(private val repository: QuizRepository) {

    operator fun invoke(): Flow<List<Problem>> = repository.getWrongProblems().map { it.reversed() }

}
