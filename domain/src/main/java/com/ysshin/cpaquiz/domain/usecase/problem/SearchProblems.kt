package com.ysshin.cpaquiz.domain.usecase.problem

import com.ysshin.cpaquiz.domain.repository.QuizRepository

class SearchProblems(private val repository: QuizRepository) {

    suspend operator fun invoke(text: String) = repository.searchProblems(text)
}
