package com.ysshin.cpaquiz.domain.usecase.quiz

import com.ysshin.cpaquiz.domain.repository.QuizRepository

class SetQuizNumber(private val repository: QuizRepository) {

    suspend operator fun invoke(value: Int) = repository.setQuizNumber(value)

}
