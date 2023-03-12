package com.ysshin.cpaquiz.feature.quiz.presentation.screen.quizresult

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ysshin.cpaquiz.core.android.base.BaseViewModel
import com.ysshin.cpaquiz.domain.model.WrongProblem
import com.ysshin.cpaquiz.domain.usecase.problem.ProblemUseCases
import com.ysshin.cpaquiz.domain.usecase.quiz.QuizUseCases
import com.ysshin.cpaquiz.feature.quiz.presentation.mapper.toDomain
import com.ysshin.cpaquiz.feature.quiz.presentation.model.ProblemModel
import com.ysshin.cpaquiz.feature.quiz.presentation.util.QuizConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber

@HiltViewModel
class QuizResultViewModel @Inject constructor(
    private val problemUseCases: ProblemUseCases,
    quizUseCases: QuizUseCases,
    handle: SavedStateHandle,
) : BaseViewModel() {

    private val _totalElapsedTime =
        MutableStateFlow(handle.get<LongArray>(QuizConstants.timesPerQuestion)?.sum() ?: 0L)

    private val _selectedIndices = handle.getStateFlow<ArrayList<Int>>(QuizConstants.selected, ArrayList())
    private val _solvedQuestions =
        handle.getStateFlow<ArrayList<ProblemModel>>(QuizConstants.problems, ArrayList())

    val quizResultUiState: StateFlow<QuizResultUiState> =
        combine(
            _totalElapsedTime,
            quizUseCases.getShouldRequestInAppReview(),
            quizUseCases.getSolvedQuiz(),
            _selectedIndices,
            _solvedQuestions,
        ) { totalElapsedTime, shouldRequestInAppReview, totalSolvedQuiz, selected, currentSolved ->
            if (selected.size == 0 || currentSolved.size == 0) {
                return@combine QuizResultUiState.Loading
            }

            val shouldShowInterstitialAd = totalSolvedQuiz > 0 && totalSolvedQuiz % SOLVED_QUIZ_THRESHOLD == 0

            // FIXME: Maybe this function call multiple, but it only call at once.
            insertWrongQuestionsToLocalDb(selected, currentSolved)

            QuizResultUiState.QuizResult(
                totalElapsedTime = totalElapsedTime,
                shouldRequestInAppReview = shouldRequestInAppReview,
                shouldShowInterstitialAd = shouldShowInterstitialAd,
                selectedIndices = selected,
                solvedQuestions = currentSolved
            )
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = QuizResultUiState.Loading
            )

    private fun insertWrongQuestionsToLocalDb(selected: List<Int>, solved: List<ProblemModel>) {
        solved
            .toDomain()
            .filterIndexed { index, question ->
                selected[index] != question.answer
            }
            .map(WrongProblem::from)
            .also { wrongQuestions ->
                problemUseCases.insertWrongProblems(wrongQuestions, viewModelScope)
                Timber.d("Insert wrong problems to local database")
            }
    }

    companion object {
        const val SOLVED_QUIZ_THRESHOLD = 5
    }
}

sealed interface QuizResultUiState {
    object Loading : QuizResultUiState

    data class QuizResult(
        val totalElapsedTime: Long,
        val shouldRequestInAppReview: Boolean,
        val shouldShowInterstitialAd: Boolean,
        val selectedIndices: List<Int>,
        val solvedQuestions: List<ProblemModel>,
    ) : QuizResultUiState
}
