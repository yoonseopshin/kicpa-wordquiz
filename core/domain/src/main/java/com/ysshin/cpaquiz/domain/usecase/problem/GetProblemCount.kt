package com.ysshin.cpaquiz.domain.usecase.problem

import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.domain.repository.QuizRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class GetProblemCount(private val repository: QuizRepository) {

    operator fun invoke(scope: CoroutineScope, onResult: (MutableMap<QuizType, Int>) -> Unit) = scope.launch {
        val countMap = mutableMapOf<QuizType, Int>()
        for (type in QuizType.all()) {
            countMap[type] = repository.getProblemCountByType(type)
        }
        onResult(countMap)
    }
}
