package com.ysshin.cpaquiz.domain.usecase.quiz

import com.ysshin.cpaquiz.domain.repository.QuizRepository

class SetUseTimer(private val repository: QuizRepository) {

    suspend operator fun invoke(value: Boolean) = repository.setUseTimer(value)
}
