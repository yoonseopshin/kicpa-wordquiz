package com.ysshin.cpaquiz.domain.usecase.quiz

import com.ysshin.cpaquiz.domain.repository.QuizRepository

class GetSolvedQuiz(private val repository: QuizRepository) {

    operator fun invoke() = repository.getSolvedQuiz()
}
