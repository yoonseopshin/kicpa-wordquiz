package com.ysshin.cpaquiz.domain.usecase.quiz

import com.ysshin.cpaquiz.domain.repository.ConfigRepository
import com.ysshin.cpaquiz.domain.repository.QuizRepository
import kotlinx.coroutines.flow.combine

class GetShouldShowInterstitialAd(
    private val quizRepository: QuizRepository,
    private val configRepository: ConfigRepository,
) {

    operator fun invoke() = combine(
        quizRepository.getShouldShowInterstitialAd(),
        configRepository.isQuizResultInterstitialAdEnabled(),
        Boolean::and,
    )

}
