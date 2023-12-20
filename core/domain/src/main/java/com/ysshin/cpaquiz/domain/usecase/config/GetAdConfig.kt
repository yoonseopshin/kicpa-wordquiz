package com.ysshin.cpaquiz.domain.usecase.config

import com.ysshin.cpaquiz.domain.model.AdType
import com.ysshin.cpaquiz.domain.repository.ConfigRepository
import kotlinx.coroutines.flow.Flow

class GetAdConfig(private val repository: ConfigRepository) {

    operator fun invoke(adType: AdType): Flow<Boolean> {
        return when (adType) {
            AdType.HomeNativeMediumAd -> repository.isHomeNativeMediumAdEnabled()
            AdType.NoteNativeSmallAd -> repository.isNoteNativeSmallAdEnabled()
            AdType.SettingsNativeMediumAd -> repository.isSettingsNativeMediumAdEnabled()
            AdType.QuizResultInterstitialAd -> repository.isQuizResultInterstitialAdEnabled()
            AdType.QuizResultNativeMediumAd -> repository.isQuizResultNativeMediumAdEnabled()
        }
    }
}
