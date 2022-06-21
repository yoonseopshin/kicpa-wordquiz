package com.ysshin.cpaquiz.domain.usecase.problem

import com.ysshin.cpaquiz.domain.model.Problem
import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.domain.repository.QuizRepository
import com.ysshin.cpaquiz.domain.util.OrderType
import com.ysshin.cpaquiz.domain.util.ProblemOrder
import com.ysshin.cpaquiz.shared.base.Consumer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class GetLocalProblems(private val repository: QuizRepository) {

    operator fun invoke(
        problemOrder: ProblemOrder = ProblemOrder.Time(OrderType.Ascending),
        years: List<Int> = Problem.allYears(),
        types: List<QuizType> = QuizType.all(),
        scope: CoroutineScope,
        onResult: Consumer<List<Problem>>,
    ) {
        scope.launch {
            onResult(problemOrder(repository.getLocalProblems(years = years, types = types)))
        }
    }
}
