package com.ysshin.cpaquiz.feature.home.presentation.screen.main

import androidx.lifecycle.viewModelScope
import com.ysshin.cpaquiz.core.android.base.BaseViewModel
import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.domain.usecase.problem.ProblemUseCases
import com.ysshin.cpaquiz.domain.usecase.quiz.QuizUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.Duration
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val problemUseCases: ProblemUseCases,
    private val quizUseCases: QuizUseCases,
) : BaseViewModel() {

    val homeQuizUiState: StateFlow<HomeQuizUiState> =
        combine(
            problemUseCases.getProblemCount(QuizType.Accounting),
            problemUseCases.getProblemCount(QuizType.Business),
            problemUseCases.getProblemCount(QuizType.CommercialLaw),
            problemUseCases.getProblemCount(QuizType.TaxLaw),
        ) { accountingCount, businessCount, commercialLawCount, taxLawCount ->
            HomeQuizUiState.Success(accountingCount, businessCount, commercialLawCount, taxLawCount)
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = HomeQuizUiState.Loading
            )

    val homeInfoUiState: StateFlow<HomeInfoUiState> =
        combine(
            quizUseCases.getNextExamDate(),
            quizUseCases.getQuizNumber(),
            quizUseCases.getUseTimer()
        ) { nextExamDate, quizNumber, useTimer ->
            val now = LocalDate.now()
            val target = LocalDate.parse(nextExamDate, DateTimeFormatter.ISO_DATE)
            val dday = Duration.between(now.atStartOfDay(), target.atStartOfDay()).toDays().toString()
            HomeInfoUiState.Success(dday, quizNumber, useTimer)
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = HomeInfoUiState.Loading
            )

    private fun setTimer(value: Boolean) {
        viewModelScope.launch {
            quizUseCases.setUseTimer(value)
        }
    }

    fun toggleTimer() {
        val infoUiState = homeInfoUiState.value
        if (infoUiState is HomeInfoUiState.Success) {
            setTimer(infoUiState.useTimer.not())
        }
    }

    fun setQuizNumber(value: Int) {
        viewModelScope.launch {
            quizUseCases.setQuizNumber(value)
        }
    }
}

sealed interface HomeQuizUiState {
    object Loading : HomeQuizUiState
    data class Success(
        val accountingCount: Int,
        val businessCount: Int,
        val commercialLawCount: Int,
        val taxLawCount: Int,
    ) : HomeQuizUiState
}

sealed interface HomeInfoUiState {
    object Loading : HomeInfoUiState
    data class Success(
        val dday: String,
        val quizNumber: Int,
        val useTimer: Boolean,
    ) : HomeInfoUiState
}
