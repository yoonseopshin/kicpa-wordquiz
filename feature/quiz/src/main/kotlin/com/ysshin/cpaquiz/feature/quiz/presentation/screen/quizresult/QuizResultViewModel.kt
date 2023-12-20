@file:Suppress("ktlint:standard:property-naming")

package com.ysshin.cpaquiz.feature.quiz.presentation.screen.quizresult

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ysshin.cpaquiz.core.android.base.BaseViewModel
import com.ysshin.cpaquiz.domain.model.AdType
import com.ysshin.cpaquiz.domain.model.WrongProblem
import com.ysshin.cpaquiz.domain.usecase.config.ConfigUseCases
import com.ysshin.cpaquiz.domain.usecase.problem.ProblemUseCases
import com.ysshin.cpaquiz.domain.usecase.shared.SharedUseCases
import com.ysshin.cpaquiz.feature.quiz.presentation.mapper.toDomain
import com.ysshin.cpaquiz.feature.quiz.presentation.model.ProblemModel
import com.ysshin.cpaquiz.feature.quiz.presentation.util.QuizConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class QuizResultViewModel @Inject constructor(
    private val problemUseCases: ProblemUseCases,
    sharedUseCases: SharedUseCases,
    configUseCases: ConfigUseCases,
    handle: SavedStateHandle,
) : BaseViewModel() {

    private val _totalElapsedTime =
        MutableStateFlow(handle.get<LongArray>(QuizConstants.TIMES_PER_QUESTION)?.sum() ?: 0L)

    private val _selectedIndices =
        handle.getStateFlow<ArrayList<Int>>(QuizConstants.SELECTED, ArrayList())
    private val _solvedQuestions =
        handle.getStateFlow<ArrayList<ProblemModel>>(QuizConstants.PROBLEMS, ArrayList())

    val isQuizResultNativeMediumAdEnabled = configUseCases.getAdConfig(AdType.QuizResultNativeMediumAd)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = true,
        )

    val quizResultUiState: StateFlow<QuizResultUiState> =
        combine(
            _totalElapsedTime,
            sharedUseCases.getShouldRequestInAppReview(),
            sharedUseCases.getShouldShowInterstitialAd(),
            _selectedIndices,
            _solvedQuestions,
        ) { totalElapsedTime, shouldRequestInAppReview, shouldShowInterstitialAd, selected, currentSolved ->
            if (selected.size == 0 || currentSolved.size == 0) {
                return@combine QuizResultUiState.Loading
            }

            insertWrongQuestionsToLocalDb(selected, currentSolved)

            QuizResultUiState.Success(
                totalElapsedTime = totalElapsedTime,
                shouldRequestInAppReview = shouldRequestInAppReview,
                shouldShowInterstitialAd = shouldShowInterstitialAd,
                selectedIndices = selected,
                solvedQuestions = currentSolved,
            )
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = QuizResultUiState.Loading,
            )

    private fun insertWrongQuestionsToLocalDb(selected: List<Int>, solved: List<ProblemModel>) {
        solved
            .toDomain()
            .filterIndexed { index, question ->
                selected[index] != question.answer
            }
            .map(WrongProblem::from)
            .also { wrongQuestions ->
                problemUseCases.upsertWrongProblems(wrongQuestions, viewModelScope)
                Timber.d("Insert wrong problems to local database $wrongQuestions")
            }
    }
}

sealed interface QuizResultUiState {
    object Loading : QuizResultUiState

    data class Success(
        val totalElapsedTime: Long,
        val shouldRequestInAppReview: Boolean,
        val shouldShowInterstitialAd: Boolean,
        val selectedIndices: List<Int>,
        val solvedQuestions: List<ProblemModel>,
    ) : QuizResultUiState
}
