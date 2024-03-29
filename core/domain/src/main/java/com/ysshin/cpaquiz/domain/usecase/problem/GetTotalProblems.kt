package com.ysshin.cpaquiz.domain.usecase.problem

import com.ysshin.cpaquiz.domain.repository.QuizRepository
import com.ysshin.cpaquiz.domain.util.OrderType
import com.ysshin.cpaquiz.domain.util.ProblemOrder
import kotlinx.coroutines.flow.map

class GetTotalProblems(private val repository: QuizRepository) {

    operator fun invoke(problemOrder: ProblemOrder = ProblemOrder.Time(OrderType.Ascending)) =
        repository.getTotalProblems().map { problemOrder(it) }
}
