package com.ysshin.cpaquiz.domain.usecase.problem

import com.ysshin.cpaquiz.domain.repository.QuizRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class DeleteAllWrongProblems(private val repository: QuizRepository) {

    operator fun invoke(scope: CoroutineScope) {
        scope.launch {
            repository.deleteAllWrongProblems()
        }
    }

}