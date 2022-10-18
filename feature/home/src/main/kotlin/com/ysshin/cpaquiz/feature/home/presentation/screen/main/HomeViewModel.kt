package com.ysshin.cpaquiz.feature.home.presentation.screen.main

import androidx.lifecycle.viewModelScope
import com.ysshin.cpaquiz.core.android.base.BaseViewModel
import com.ysshin.cpaquiz.core.common.DEFAULT_STRING
import com.ysshin.cpaquiz.domain.model.DEFAULT_QUIZ_NUMBER
import com.ysshin.cpaquiz.domain.model.DEFAULT_USE_TIMER
import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.domain.usecase.problem.ProblemUseCases
import com.ysshin.cpaquiz.domain.usecase.quiz.QuizUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.Duration
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val problemUseCases: ProblemUseCases,
    private val quizUseCases: QuizUseCases,
) : BaseViewModel() {

    val dday = quizUseCases.getNextExamDate()
        .map { nextExamDate ->
            val now = LocalDate.now()
            val target = LocalDate.parse(nextExamDate, DateTimeFormatter.ISO_DATE)
            Duration.between(now.atStartOfDay(), target.atStartOfDay()).toDays().toString()
        }
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = DEFAULT_STRING
        )

    private val _isQuizSettingsOpened = MutableStateFlow(false)
    val isQuizSettingsOpened = _isQuizSettingsOpened.asStateFlow()

    val quizNumber = quizUseCases.getQuizNumber()
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = DEFAULT_QUIZ_NUMBER
        )

    val useTimer = quizUseCases.getUseTimer()
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = DEFAULT_USE_TIMER
        )

    val accountingCount = problemUseCases.getProblemCount(QuizType.Accounting)
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = 0
        )

    val businessCount = problemUseCases.getProblemCount(QuizType.Business)
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = 0
        )

    val commercialLawCount = problemUseCases.getProblemCount(QuizType.CommercialLaw)
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = 0
        )

    val taxLawCount = problemUseCases.getProblemCount(QuizType.TaxLaw)
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = 0
        )

    fun setTimer(value: Boolean) {
        viewModelScope.launch {
            quizUseCases.setUseTimer(value)
        }
    }

    fun toggleTimer() {
        setTimer(useTimer.value.not())
    }

    fun setQuizSettingsOpened(value: Boolean) {
        _isQuizSettingsOpened.value = value
    }

    fun setQuizNumber(value: Int) {
        viewModelScope.launch {
            quizUseCases.setQuizNumber(value)
        }
    }
}
