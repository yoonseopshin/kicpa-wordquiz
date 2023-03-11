package com.ysshin.cpaquiz.domain.usecase.problem

import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.domain.repository.QuizRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class GetSubtypesByQuizType(private val repository: QuizRepository) {

    operator fun invoke(scope: CoroutineScope, onResult: (MutableMap<QuizType, List<String>>) -> Unit) =
        scope.launch {
            val subtypesMap = mutableMapOf<QuizType, List<String>>()
            for (type in QuizType.all()) {
                val subtype = repository.getSubtypesByQuizType(type)
                if (subtype.isNotEmpty()) {
                    subtypesMap[type] = subtype
                }
            }
            onResult(subtypesMap)
        }
}
