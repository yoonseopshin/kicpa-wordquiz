package com.ysshin.cpaquiz.core.android.util

object AdConstants {
    const val QUIZ_END_INTERSTITIAL_AD = "ca-app-pub-5004953701825085/1812204144"
    private const val QUIZ_NATIVE_AD_MEDIUM = "ca-app-pub-5004953701825085/8668739610"
    private const val QUIZ_NATIVE_AD_SMALL = "ca-app-pub-5004953701825085/7077236595"
    private const val DEBUG_QUIZ_NATIVE_AD = "ca-app-pub-3940256099942544/2247696110"

    // FIXME: How do I check if app has been downloaded from the Google Play Store?
    // Checking with hardcoded variables for now
    private val isMarketRelease = true

    fun getNativeMediumAdUnitId() =
        when (isMarketRelease) {
            true -> QUIZ_NATIVE_AD_MEDIUM
            false -> DEBUG_QUIZ_NATIVE_AD
        }

    fun getNativeSmallAdUnitId() =
        when (isMarketRelease) {
            true -> QUIZ_NATIVE_AD_SMALL
            false -> DEBUG_QUIZ_NATIVE_AD
        }
}
