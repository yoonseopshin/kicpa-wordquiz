package com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem

import com.cpa.cpa_word_problem.feature.quiz.domain.model.QuizType
import com.cpa.cpa_word_problem.feature.quiz.domain.repository.QuizRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class DeleteWrongProblem(private val repository: QuizRepository) {

    operator fun invoke(year: Int, pid: Int, type: QuizType, scope: CoroutineScope) {
        scope.launch {
            repository.deleteWrongProblem(year, pid, type)
        }
    }

}