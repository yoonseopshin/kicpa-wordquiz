package com.ysshin.cpaquiz.data.network.api

import com.ysshin.cpaquiz.data.network.ProblemResponse
import com.ysshin.cpaquiz.data.network.ScheduledDateResponse
import retrofit2.http.GET

interface QuizService {
    @GET("cpa-data.json")
    suspend fun getCpaProblems(): List<ProblemResponse>

    @GET("cpa-scheduled.json")
    suspend fun getCpaScheduledDate(): List<ScheduledDateResponse>
}
