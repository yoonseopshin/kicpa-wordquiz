package com.ysshin.cpaquiz.domain.usecase.problem

import com.ysshin.cpaquiz.domain.model.Problem
import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.domain.repository.QuizRepository
import com.ysshin.cpaquiz.shared.base.Consumer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class GetWrongProblems(private val repository: QuizRepository) {

    operator fun invoke(
        years: List<Int> = Problem.allYears(),
        types: List<QuizType> = QuizType.all(),
        scope: CoroutineScope,
        onResult: Consumer<List<Problem>>,
    ) {
        scope.launch {
            onResult(repository.getWrongProblems(years = years, types = types).reversed())
        }
    }
}
