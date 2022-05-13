package com.ysshin.cpaquiz.domain.usecase.problem

import com.ysshin.cpaquiz.domain.model.Problem
import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.domain.repository.QuizRepository
import com.ysshin.cpaquiz.shared.base.Consumer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class GetProblem(private val repository: QuizRepository) {

    suspend operator fun invoke(
        type: QuizType,
        scope: CoroutineScope,
        onResult: Consumer<Problem>
    ) {
        scope.launch {
            onResult(repository.getLocalProblem(type))
        }
    }

}