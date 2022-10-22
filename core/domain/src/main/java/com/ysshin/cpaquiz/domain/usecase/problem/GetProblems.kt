package com.ysshin.cpaquiz.domain.usecase.problem

import com.ysshin.cpaquiz.core.common.Consumer
import com.ysshin.cpaquiz.domain.model.Problem
import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.domain.repository.QuizRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class GetProblems(private val repository: QuizRepository) {

    suspend operator fun invoke(
        type: QuizType,
        size: Int,
        scope: CoroutineScope,
        onResult: Consumer<List<Problem>>
    ) {
        scope.launch {
            onResult(repository.getTotalProblems(type, size))
        }
    }
}
