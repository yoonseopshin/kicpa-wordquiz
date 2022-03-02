package com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem

import com.cpa.cpa_word_problem.feature.quiz.domain.model.WrongProblem
import com.cpa.cpa_word_problem.feature.quiz.domain.repository.QuizRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class InsertWrongProblems(private val repository: QuizRepository) {

    operator fun invoke(wrongProblems: List<WrongProblem>, scope: CoroutineScope) {
        scope.launch {
            repository.insertWrongProblems(wrongProblems)
        }
    }

}