package com.cpa.cpa_word_problem.feature.quiz.domain.usecase.quiz

import com.cpa.cpa_word_problem.feature.quiz.domain.repository.QuizRepository

class GetNextExamDate(private val repository: QuizRepository) {

    suspend operator fun invoke(): String = repository.getNextExamDate()

}