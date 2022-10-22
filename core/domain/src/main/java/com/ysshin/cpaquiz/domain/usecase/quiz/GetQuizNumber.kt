package com.ysshin.cpaquiz.domain.usecase.quiz

import com.ysshin.cpaquiz.domain.repository.QuizRepository

class GetQuizNumber(private val quizRepository: QuizRepository) {

    operator fun invoke() = quizRepository.getQuizNumber()
}
