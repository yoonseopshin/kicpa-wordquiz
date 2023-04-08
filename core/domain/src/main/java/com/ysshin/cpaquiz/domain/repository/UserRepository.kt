package com.ysshin.cpaquiz.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getPostNotification(): Flow<Int>

    suspend fun grantPostNotification()

    suspend fun denyPostNotification()
}
