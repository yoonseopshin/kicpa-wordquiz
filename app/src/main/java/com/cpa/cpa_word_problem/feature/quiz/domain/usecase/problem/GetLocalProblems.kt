package com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem

import com.cpa.cpa_word_problem.feature.quiz.domain.model.Problem
import com.cpa.cpa_word_problem.feature.quiz.domain.repository.QuizRepository
import com.cpa.cpa_word_problem.feature.quiz.domain.util.OrderType
import com.cpa.cpa_word_problem.feature.quiz.domain.util.ProblemOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetLocalProblems(private val repository: QuizRepository) {

    operator fun invoke(problemOrder: ProblemOrder = ProblemOrder.Time(OrderType.Ascending)): Flow<List<Problem>> =
        repository.getLocalProblems().map { problemOrder(it) }

}