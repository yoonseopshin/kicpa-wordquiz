package com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem

import com.cpa.cpa_word_problem.feature.quiz.domain.repository.QuizRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class DeleteAllWrongProblems(private val repository: QuizRepository) {

    operator fun invoke(scope: CoroutineScope) {
        scope.launch {
            repository.deleteAllWrongProblems()
        }
    }

}