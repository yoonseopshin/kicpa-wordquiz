package com.ysshin.cpaquiz.domain.usecase.quiz

import com.ysshin.cpaquiz.domain.repository.QuizRepository
import kotlinx.coroutines.flow.Flow

class GetNextExamDate(private val repository: QuizRepository) {

    operator fun invoke(): Flow<String> = repository.getNextExamDate()
}
