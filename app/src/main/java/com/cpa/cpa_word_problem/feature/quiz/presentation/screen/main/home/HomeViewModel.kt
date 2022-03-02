package com.cpa.cpa_word_problem.feature.quiz.presentation.screen.main.home

import androidx.lifecycle.viewModelScope
import com.cpa.cpa_word_problem.base.BaseViewModel
import com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.QuizDatastoreManager
import com.cpa.cpa_word_problem.feature.quiz.domain.usecase.quiz.QuizUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val quizUseCases: QuizUseCases,
    private val quizDatastoreManager: QuizDatastoreManager,
) : BaseViewModel() {

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
            initialValue = QuizDatastoreManager.USE_TIMER
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

}