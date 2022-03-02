package com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem

import com.cpa.cpa_word_problem.feature.quiz.domain.repository.QuizRepository

class SyncRemoteProblems(private val repository: QuizRepository) {

    suspend operator fun invoke() {
        repository.syncRemoteProblems()
    }

}