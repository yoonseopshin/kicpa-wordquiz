package com.ysshin.cpaquiz.domain.usecase.problem

import com.ysshin.cpaquiz.domain.repository.QuizRepository

class SyncRemoteProblems(private val repository: QuizRepository) {

    suspend operator fun invoke() {
        repository.syncRemoteProblems()
    }
}
