package com.ysshin.cpaquiz.feature.home.presentation.navigation

import com.ysshin.cpaquiz.core.android.base.BaseActivity
import com.ysshin.cpaquiz.domain.model.QuizType

interface QuizStartNavigationActions {
    fun onQuizStart(
        activity: BaseActivity,
        quizType: QuizType,
        quizNumbers: Int,
        useTimer: Boolean,
        selectedSubtypes: List<String>,
    )
}
