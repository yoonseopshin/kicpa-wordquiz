package com.ysshin.cpaquiz.data.repository

import com.ysshin.cpaquiz.core.datastore.AppPreferenceDataSource
import com.ysshin.cpaquiz.core.network.api.RemoteApi
import com.ysshin.cpaquiz.data.mapper.toDomain
import com.ysshin.cpaquiz.domain.repository.ConfigRepository
import javax.inject.Inject

class ConfigRepositoryImpl @Inject constructor(
    private val remoteApi: RemoteApi,
    private val appPreferenceDataSource: AppPreferenceDataSource,
) : ConfigRepository {

    override suspend fun syncConfigs() {
        val configResponse = remoteApi.getAppConfig()
        appPreferenceDataSource.updateAdConfig(configResponse.toDomain())
    }

    override fun isHomeNativeMediumAdEnabled() = appPreferenceDataSource.isHomeNativeMediumAdEnabled
    override fun isNoteNativeSmallAdEnabled() = appPreferenceDataSource.isNoteNativeSmallAdEnabled

    override fun isSettingsNativeMediumAdEnabled() =
        appPreferenceDataSource.isSettingsNativeMediumAdEnabled

    override fun isQuizResultInterstitialAdEnabled() =
        appPreferenceDataSource.isQuizResultInterstitialAdEnabled

    override fun isQuizResultNativeMediumAdEnabled() =
        appPreferenceDataSource.isQuizResultNativeMediumAdEnabled
}
