package com.cpa.cpa_word_problem.data.repository

import com.cpa.cpa_word_problem.data.model.local.ProblemDao
import com.cpa.cpa_word_problem.domain.core.ApiResult
import com.cpa.cpa_word_problem.domain.model.Problem
import com.cpa.cpa_word_problem.domain.repository.QuizRepository

class QuizRepositoryImpl(private val problemDao: ProblemDao) : QuizRepository {
    override suspend fun makeProblemRequest(): ApiResult<List<Problem>> {
        TODO("Not yet implemented")
    }

    override suspend fun getProblems(): List<Problem> {
        TODO("Not yet implemented")
    }
}