package com.ysshin.cpaquiz.domain.usecase.quiz

import com.ysshin.cpaquiz.domain.repository.QuizRepository

class GetNextExamDate(private val repository: QuizRepository) {

    suspend operator fun invoke(): String = repository.getNextExamDate()

}