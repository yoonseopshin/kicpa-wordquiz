package com.ysshin.cpaquiz.core.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AppConfigResponse(
    @SerialName("home_native_medium_ad")
    val homeNativeMediumAd: Boolean,
    @SerialName("note_native_small_ad")
    val noteNativeSmallAd: Boolean,
    @SerialName("settings_native_medium_ad")
    val settingsNativeMediumAd: Boolean,
    @SerialName("quiz_result_interstitial_ad")
    val quizResultInterstitialAd: Boolean,
    @SerialName("quiz_result_native_medium_ad")
    val quizResultNativeMediumAd: Boolean,
)
