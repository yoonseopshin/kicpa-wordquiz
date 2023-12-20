package com.ysshin.cpaquiz.domain.repository

import kotlinx.coroutines.flow.Flow

interface ConfigRepository {
    suspend fun syncConfigs()

    fun isHomeNativeMediumAdEnabled(): Flow<Boolean>
    fun isNoteNativeSmallAdEnabled(): Flow<Boolean>
    fun isSettingsNativeMediumAdEnabled(): Flow<Boolean>
    fun isQuizResultInterstitialAdEnabled(): Flow<Boolean>
    fun isQuizResultNativeMediumAdEnabled(): Flow<Boolean>

}