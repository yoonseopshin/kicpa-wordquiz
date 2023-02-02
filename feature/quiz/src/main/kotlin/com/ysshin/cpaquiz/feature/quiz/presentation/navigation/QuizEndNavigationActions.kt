package com.ysshin.cpaquiz.feature.quiz.presentation.navigation

import com.ysshin.cpaquiz.core.android.base.BaseActivity
import com.ysshin.cpaquiz.domain.model.Problem

interface QuizEndNavigationActions {
    fun onQuizEnd(
        activity: BaseActivity,
        problems: List<Problem> = listOf(),
        selected: List<Int> = listOf(),
        timesPerQuestion: List<Long> = listOf(),
    )
}
