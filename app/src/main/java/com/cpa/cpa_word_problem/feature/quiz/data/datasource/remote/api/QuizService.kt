package com.cpa.cpa_word_problem.feature.quiz.data.datasource.remote.api

import com.cpa.cpa_word_problem.feature.quiz.data.datasource.remote.ProblemResponse
import com.cpa.cpa_word_problem.feature.quiz.data.datasource.remote.ScheduledDateResponse
import retrofit2.http.GET

interface QuizService {
    @GET("cpa-data.json")
    suspend fun getCpaProblems(): List<ProblemResponse>

    @GET("cpa-scheduled.json")
    suspend fun getCpaScheduledDate(): List<ScheduledDateResponse>
}