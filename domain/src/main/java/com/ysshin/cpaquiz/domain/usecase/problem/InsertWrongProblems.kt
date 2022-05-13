package com.ysshin.cpaquiz.domain.usecase.problem

import com.ysshin.cpaquiz.domain.model.WrongProblem
import com.ysshin.cpaquiz.domain.repository.QuizRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class InsertWrongProblems(private val repository: QuizRepository) {

    operator fun invoke(wrongProblems: List<WrongProblem>, scope: CoroutineScope) {
        scope.launch {
            repository.insertWrongProblems(wrongProblems)
        }
    }

}