package com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem

import com.cpa.cpa_word_problem.feature.quiz.domain.model.QuizType
import com.cpa.cpa_word_problem.feature.quiz.domain.repository.QuizRepository

class GetProblemCount(private val repository: QuizRepository) {

    operator fun invoke(type: QuizType) = repository.getProblemCountByType(type)

}