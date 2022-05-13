package com.ysshin.cpaquiz.domain.usecase.problem

import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.domain.repository.QuizRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class DeleteWrongProblem(private val repository: QuizRepository) {

    operator fun invoke(year: Int, pid: Int, type: QuizType, scope: CoroutineScope) {
        scope.launch {
            repository.deleteWrongProblem(year, pid, type)
        }
    }

}