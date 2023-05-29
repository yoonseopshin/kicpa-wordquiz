package com.ysshin.cpaquiz.data.repository

import com.ysshin.cpaquiz.core.datastore.AppPreferenceDataSource
import com.ysshin.cpaquiz.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val appPreferenceDataSource: AppPreferenceDataSource,
) : UserRepository {

    override fun getPostNotification() = appPreferenceDataSource.postNotification

    override suspend fun grantPostNotification() = appPreferenceDataSource.grantPostNotification()

    override suspend fun denyPostNotification() = appPreferenceDataSource.denyPostNotification()
}
