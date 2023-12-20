package com.ysshin.cpaquiz.domain.model

data class AppConfig(
    val homeNativeMediumAd: AdConfig,
    val noteNativeSmallAd: AdConfig,
    val settingsNativeMediumAd: AdConfig,
    val quizResultInterstitialAd: AdConfig,
    val quizResultNativeMediumAd: AdConfig,
)

data class AdConfig(val type: AdType, val on: Boolean)

enum class AdType {
    HomeNativeMediumAd,
    NoteNativeSmallAd,
    SettingsNativeMediumAd,
    QuizResultInterstitialAd,
    QuizResultNativeMediumAd,
}
