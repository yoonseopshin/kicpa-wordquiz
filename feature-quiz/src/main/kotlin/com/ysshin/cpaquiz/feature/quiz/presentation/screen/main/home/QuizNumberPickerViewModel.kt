package com.ysshin.cpaquiz.feature.quiz.presentation.screen.main.home

import androidx.lifecycle.viewModelScope
import com.ysshin.cpaquiz.domain.usecase.quiz.QuizUseCases
import com.ysshin.cpaquiz.shared.android.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class QuizNumberPickerViewModel @Inject constructor(
    private val quizUseCases: QuizUseCases
) : BaseViewModel() {

    fun setQuizNumber(value: Int) {
        viewModelScope.launch {
            quizUseCases.setQuizNumber(value)
        }
    }
}
