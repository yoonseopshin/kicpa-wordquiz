package com.ysshin.cpaquiz.feature.home.presentation.navigation

import com.ysshin.cpaquiz.domain.model.QuizType

interface QuizNavigationActions {
    fun onQuizStart(quizType: QuizType, quizNumbers: Int, useTimer: Boolean)
}
