package com.ysshin.cpaquiz.domain.usecase.quiz

import com.ysshin.cpaquiz.domain.repository.QuizRepository

class IncreaseSolvedQuiz(private val repository: QuizRepository) {

    suspend operator fun invoke() = repository.increaseSolvedQuiz()

}
