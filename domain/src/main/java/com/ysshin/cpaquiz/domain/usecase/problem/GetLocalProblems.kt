package com.ysshin.cpaquiz.domain.usecase.problem

import com.ysshin.cpaquiz.domain.model.Problem
import com.ysshin.cpaquiz.domain.repository.QuizRepository
import com.ysshin.cpaquiz.domain.util.OrderType
import com.ysshin.cpaquiz.domain.util.ProblemOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetLocalProblems(private val repository: QuizRepository) {

    operator fun invoke(problemOrder: ProblemOrder = ProblemOrder.Time(OrderType.Ascending)): Flow<List<Problem>> =
            repository.getLocalProblems().map { problemOrder(it) }

}