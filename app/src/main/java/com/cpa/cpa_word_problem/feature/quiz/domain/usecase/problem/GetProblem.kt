package com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem

import com.cpa.cpa_word_problem.feature.quiz.domain.model.Problem
import com.cpa.cpa_word_problem.feature.quiz.domain.model.QuizType
import com.cpa.cpa_word_problem.feature.quiz.domain.repository.QuizRepository
import com.ysshin.shared.util.Consumer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class GetProblem(private val repository: QuizRepository) {

    suspend operator fun invoke(
        type: QuizType,
        scope: CoroutineScope,
        onResult: Consumer<Problem>
    ) {
        scope.launch {
            onResult(repository.getLocalProblem(type))
        }
    }

}