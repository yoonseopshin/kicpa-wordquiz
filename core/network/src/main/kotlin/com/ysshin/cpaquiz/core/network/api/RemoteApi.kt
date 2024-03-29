package com.ysshin.cpaquiz.core.network.api

import com.ysshin.cpaquiz.core.network.response.AppConfigResponse
import com.ysshin.cpaquiz.core.network.response.ProblemResponse
import com.ysshin.cpaquiz.core.network.response.ScheduledDateResponse
import retrofit2.http.GET

interface RemoteApi {
    @GET("cpa-data.json")
    suspend fun getCpaProblems(): List<ProblemResponse>

    @GET("cpa-scheduled.json")
    suspend fun getCpaScheduledDate(): List<ScheduledDateResponse>

    @GET("app-config.json")
    suspend fun getAppConfig(): AppConfigResponse
}
