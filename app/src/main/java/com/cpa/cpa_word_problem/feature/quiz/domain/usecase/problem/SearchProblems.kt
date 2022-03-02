package com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem

import com.cpa.cpa_word_problem.feature.quiz.domain.repository.QuizRepository

class SearchProblems(private val repository: QuizRepository) {

    suspend operator fun invoke(text: String) = repository.searchProblems(text)

}
