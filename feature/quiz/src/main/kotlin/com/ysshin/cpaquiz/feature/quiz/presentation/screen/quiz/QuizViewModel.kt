package com.ysshin.cpaquiz.feature.quiz.presentation.screen.quiz

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ysshin.cpaquiz.core.android.base.BaseViewModel
import com.ysshin.cpaquiz.core.android.util.UiText
import com.ysshin.cpaquiz.core.base.DEFAULT_INT
import com.ysshin.cpaquiz.domain.model.Problem
import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.domain.usecase.problem.ProblemUseCases
import com.ysshin.cpaquiz.domain.usecase.quiz.QuizUseCases
import com.ysshin.cpaquiz.feature.quiz.R
import com.ysshin.cpaquiz.feature.quiz.presentation.util.QuizConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import com.ysshin.cpaquiz.core.android.R as CR

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val problemUseCases: ProblemUseCases,
    private val quizUseCases: QuizUseCases,
    private val handle: SavedStateHandle,
) : BaseViewModel() {

    val useTimer: StateFlow<Boolean> = handle.getStateFlow(QuizConstants.USE_TIMER, false)
    private val timer = QuizTimer(viewModelScope).also { timer ->
        if (useTimer.value) timer.start()
    }
    val elapsedTime = timer.elapsedTime.asStateFlow()

    private val quizNumbers: Int = handle[QuizConstants.QUIZ_NUMBERS] ?: DEFAULT_INT
    private val selectedSubtypes: List<String> =
        handle.get<ArrayList<String>>(QuizConstants.SELECTED_SUBTYPES) ?: emptyList()

    val numOfTotalQuestions = MutableStateFlow(quizNumbers).asStateFlow()

    private val _numOfSolvedQuestions = MutableStateFlow(0)
    val numOfSolvedQuestions = _numOfSolvedQuestions.asStateFlow()

    val quizState = MutableStateFlow<QuizState>(QuizState.Solving)
    val snackbarState = MutableStateFlow<SnackbarState>(SnackbarState.Hide)

    val quizType = handle.get<QuizType>(QuizConstants.QUIZ_TYPE) ?: QuizType.Accounting

    private val _questions = mutableListOf<Problem>()
    val questions: List<Problem> = _questions

    private val _currentQuestion = MutableStateFlow(Problem.default())
    val currentQuestion = _currentQuestion.asStateFlow()

    private val _selected = mutableListOf<Int>()
    val selected: List<Int> = _selected

    val timesPerQuestion get() = timer.timesPerQuestion

    private val _isAnimationShowing = MutableStateFlow(false)
    val isAnimationShowing = _isAnimationShowing.asStateFlow()

    private val _animationInfo =
        MutableStateFlow<PopScaleAnimationInfo>(PopScaleAnimationInfo.Correct)
    val animationInfo = _animationInfo.asStateFlow()

    fun onPause() {
        if (useTimer.value.not()) return
        timer.pause()
    }

    fun onResume() {
        if (useTimer.value.not()) return
        timer.start()
    }

    private fun onQuizNext() = viewModelScope.launch {
        if (_questions.isEmpty()) return@launch

        val numOfSolvedQuestions = _numOfSolvedQuestions.value
        val numOfTotalQuestions = _questions.size

        if (numOfSolvedQuestions > numOfTotalQuestions) {
            // Can't be reached
            Timber.d("Solved number($numOfSolvedQuestions) can't be more than total number($numOfTotalQuestions)")
            return@launch
        } else if (numOfSolvedQuestions < numOfTotalQuestions) {
            // Next quiz
            _currentQuestion.value = _questions[numOfSolvedQuestions]
            _numOfSolvedQuestions.update {
                if (numOfSolvedQuestions > 0) timer.record()
                numOfSolvedQuestions + 1
            }
            _selectedQuestionIndex.value = -1
            quizState.value = QuizState.Solving
        } else {
            // End up quiz
            timer.record()
            timer.pause()
            quizUseCases.increaseSolvedQuiz()
            quizState.value = QuizState.End
        }
    }

    private val _selectedQuestionIndex = MutableStateFlow(-1)
    val selectedQuestionIndex = _selectedQuestionIndex.asStateFlow()

    fun selectQuestion(index: Int) {
        _selectedQuestionIndex.value = index
    }

    fun selectAnswer() = viewModelScope.launch {
        if (quizState.value != QuizState.Solving) return@launch

        val currentSelectedIndex = selectedQuestionIndex.value

        if (currentSelectedIndex !in 0..4) {
            snackbarState.value =
                SnackbarState.Show(message = UiText.StringResource(R.string.msg_need_answer))
            quizState.value = QuizState.Solving
            return@launch
        }

        _isAnimationShowing.value = true
        _selected.add(currentSelectedIndex)

        onGrading()
        onQuizNext()
    }

    private suspend fun onGrading() {
        quizState.value = QuizState.Grading
        val selectedQuestion = _selectedQuestionIndex.value
        val answer = _currentQuestion.value.answer

        if (selectedQuestion == answer) {
            _animationInfo.value = PopScaleAnimationInfo.Correct
            Timber.d("Correct answer")
        } else {
            _animationInfo.value = PopScaleAnimationInfo.Incorrect
            Timber.d("Incorrect answer")
        }
        delay(300L)
        _isAnimationShowing.value = false
    }

    init {
        viewModelScope.launch {
            problemUseCases.getProblems(quizType, quizNumbers, selectedSubtypes, this) {
                _questions.clear()
                _questions.addAll(it)
                onQuizNext()
            }
        }
    }
}

sealed class PopScaleAnimationInfo(
    val backgroundColorResId: Int,
    val iconTintColorResId: Int,
    val icon: ImageVector,
) {
    object Correct :
        PopScaleAnimationInfo(
            R.color.color_on_correct,
            CR.color.daynight_pastel_green,
            Icons.Default.Check,
        )

    object Incorrect :
        PopScaleAnimationInfo(
            R.color.color_on_incorrect,
            CR.color.daynight_pastel_red,
            Icons.Default.Close,
        )
}

sealed interface SnackbarState {
    object Hide : SnackbarState
    data class Show(
        val message: UiText,
        val actionLabel: UiText = UiText.DynamicString(""),
    ) : SnackbarState
}

sealed interface QuizState {
    object Solving : QuizState

    object Grading : QuizState
    object End : QuizState
}
