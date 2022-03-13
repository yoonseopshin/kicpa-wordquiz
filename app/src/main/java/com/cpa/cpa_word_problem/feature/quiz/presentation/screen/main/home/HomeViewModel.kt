package com.cpa.cpa_word_problem.feature.quiz.presentation.screen.main.home

import androidx.lifecycle.viewModelScope
import com.cpa.cpa_word_problem.base.BaseViewModel
import com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.QuizDatastoreManager
import com.cpa.cpa_word_problem.feature.quiz.domain.model.QuizType
import com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem.ProblemUseCases
import com.cpa.cpa_word_problem.feature.quiz.domain.usecase.quiz.QuizUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    problemUseCases: ProblemUseCases,
    private val quizUseCases: QuizUseCases,
    private val quizDatastoreManager: QuizDatastoreManager,
) : BaseViewModel() {

    private val _homeState = MutableSharedFlow<HomeState>()
    val homeState = _homeState.asSharedFlow()

    val nextExamDate = MutableStateFlow("")

    val quizNumber = quizDatastoreManager.quizNumber
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = QuizDatastoreManager.DEFAULT_QUIZ_NUMBER
        )

    val useTimer = quizDatastoreManager.useTimer
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = QuizDatastoreManager.DEFAULT_USE_TIMER
        )

    val useAlarm = quizDatastoreManager.useAlarm
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = QuizDatastoreManager.DEFAULT_USE_ALARM
        )

    val alarmHourOfDay = quizDatastoreManager.alarmHourOfDay
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = QuizDatastoreManager.DEFAULT_ALARM_TIME
        )

    val alarmMinute = quizDatastoreManager.alarmMinute
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = QuizDatastoreManager.DEFAULT_ALARM_TIME
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

    fun requestNextExamDate() {
        viewModelScope.launch {
            nextExamDate.value = quizUseCases.getNextExamDate()
        }
    }

    fun setQuizNumber(value: Int) {
        viewModelScope.launch {
            quizDatastoreManager.setQuizNumber(value)
        }
    }

    fun setTimer(value: Boolean) {
        viewModelScope.launch {
            quizDatastoreManager.setTimer(value)
        }
    }

    fun setAlarm(value: Boolean) {
        viewModelScope.launch {
            if (useAlarm.value != value && value) {
                showTimePicker()
            }

            quizDatastoreManager.setAlarm(value)
        }
    }

    fun showTimePicker() {
        viewModelScope.launch {
            _homeState.emit(HomeState.ShowTimePicker)
        }
    }

    fun isAlarmSet() = alarmHourOfDay.value >= 0L && alarmMinute.value >= 0L

    fun setAlarmTime(hourOfDay: Int, minute: Int) {
        viewModelScope.launch {
            quizDatastoreManager.setAlarmHourOfDay(hourOfDay)
            quizDatastoreManager.setAlarmMinute(minute)
        }
    }

}