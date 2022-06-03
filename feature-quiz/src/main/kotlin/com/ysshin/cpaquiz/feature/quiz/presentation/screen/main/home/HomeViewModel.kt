package com.ysshin.cpaquiz.feature.quiz.presentation.screen.main.home

import androidx.lifecycle.viewModelScope
import com.ysshin.cpaquiz.domain.model.DEFAULT_QUIZ_NUMBER
import com.ysshin.cpaquiz.domain.model.DEFAULT_USE_TIMER
import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.domain.usecase.problem.ProblemUseCases
import com.ysshin.cpaquiz.domain.usecase.quiz.QuizUseCases
import com.ysshin.cpaquiz.shared.android.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val problemUseCases: ProblemUseCases,
    private val quizUseCases: QuizUseCases,
) : BaseViewModel() {

    val nextExamDate = MutableStateFlow("")

    private val _isQuizSettingsOpened = MutableStateFlow(false)
    val isQuizSettingsOpened = _isQuizSettingsOpened.asStateFlow()

    val quizNumber = quizUseCases.getQuizNumber()
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = DEFAULT_QUIZ_NUMBER
        )

    val useTimer = quizUseCases.getUseTimer()
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = DEFAULT_USE_TIMER
        )

    val accountingCount = problemUseCases.getProblemCount(QuizType.Accounting)
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = 0
        )

    val businessCount = problemUseCases.getProblemCount(QuizType.Business)
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = 0
        )

    val commercialLawCount = problemUseCases.getProblemCount(QuizType.CommercialLaw)
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = 0
        )

    val taxLawCount = problemUseCases.getProblemCount(QuizType.TaxLaw)
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = 0
        )

    fun requestNextExamDate() {
        viewModelScope.launch {
            nextExamDate.value = quizUseCases.getNextExamDate()
        }
    }

    fun setTimer(value: Boolean) {
        viewModelScope.launch {
            quizUseCases.setUseTimer(value)
        }
    }

    fun setQuizSettingsOpened(value: Boolean) {
        _isQuizSettingsOpened.value = value
    }
}
