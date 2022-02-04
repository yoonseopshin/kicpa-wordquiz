package com.cpa.cpa_word_problem.domain.repository

import com.cpa.cpa_word_problem.domain.core.ApiResult
import com.cpa.cpa_word_problem.domain.model.Problem

interface QuizRepository {
    suspend fun makeProblemRequest(): ApiResult<List<Problem>>
    suspend fun getProblems(): List<Problem>
}