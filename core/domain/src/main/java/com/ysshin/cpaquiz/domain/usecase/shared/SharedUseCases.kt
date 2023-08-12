package com.ysshin.cpaquiz.domain.usecase.shared

import com.ysshin.cpaquiz.domain.usecase.quiz.GetShouldRequestInAppReview
import com.ysshin.cpaquiz.domain.usecase.quiz.GetShouldShowInterstitialAd

data class SharedUseCases(
    val getShouldRequestInAppReview: GetShouldRequestInAppReview,
    val getShouldShowInterstitialAd: GetShouldShowInterstitialAd,
)
