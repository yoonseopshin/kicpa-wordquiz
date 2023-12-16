package com.ysshin.cpaquiz.domain.usecase.problem

import com.ysshin.cpaquiz.core.base.Consumer
import com.ysshin.cpaquiz.core.base.times
import com.ysshin.cpaquiz.domain.model.Problem
import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.domain.repository.QuizRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.ceil

class GetProblems(private val repository: QuizRepository) {

    suspend operator fun invoke(
        type: QuizType,
        size: Int,
        selectedSubtypes: List<String>,
        scope: CoroutineScope,
        onResult: Consumer<List<Problem>>,
    ) {
        scope.launch {
            if (selectedSubtypes.isEmpty()) {
                val questions = repository.getTotalProblems(type, size)
                onResult(questions)
            } else {
                val questions = repository.getTotalProblems(type, selectedSubtypes)

                if (size > questions.size) {
                    val times = ceil(size.toDouble() / questions.size).toInt()
                    onResult((questions * times).shuffled().subList(0, size))
                } else {
                    onResult(questions.subList(0, size))
                }
            }
        }
    }
}
