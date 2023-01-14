package com.ysshin.cpaquiz.feature.home.presentation.screen.main

import androidx.lifecycle.viewModelScope
import com.ysshin.cpaquiz.core.android.base.BaseViewModel
import com.ysshin.cpaquiz.core.common.zip
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
        zip(
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
            HomeInfoUiState(dday, quizNumber, useTimer)
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = HomeInfoUiState(
                    dday = "",
                    quizNumber = DEFAULT_QUIZ_NUMBER,
                    useTimer = DEFAULT_USE_TIMER
                )
            )

    private fun setTimer(value: Boolean) {
        viewModelScope.launch {
            quizUseCases.setUseTimer(value)
        }
    }

    fun toggleTimer() {
        setTimer(homeInfoUiState.value.useTimer.not())
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

data class HomeInfoUiState(
    val dday: String,
    val quizNumber: Int,
    val useTimer: Boolean,
)
