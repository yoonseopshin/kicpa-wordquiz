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
import com.ysshin.cpaquiz.domain.model.ProblemDetailMode
import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.domain.usecase.problem.ProblemUseCases
import com.ysshin.cpaquiz.domain.usecase.quiz.QuizUseCases
import com.ysshin.cpaquiz.feature.quiz.R
import com.ysshin.cpaquiz.feature.quiz.presentation.mapper.toDomain
import com.ysshin.cpaquiz.feature.quiz.presentation.model.ProblemModel
import com.ysshin.cpaquiz.feature.quiz.presentation.util.QuizConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class QuestionViewModel @Inject constructor(
    private val problemUseCases: ProblemUseCases,
    private val quizUseCases: QuizUseCases,
    private val handle: SavedStateHandle,
) : BaseViewModel() {

    val useTimer: StateFlow<Boolean> = handle.getStateFlow(QuizConstants.useTimer, false)

    private val timer = QuizTimer(viewModelScope).also { timer ->
        if (useTimer.value) timer.start()
    }
    val elapsedTime = timer.elapsedTime.asStateFlow()

    private val _isToolbarTitleVisible = MutableStateFlow(false)
    val isToolbarTitleVisible = _isToolbarTitleVisible.asStateFlow()

    private val _isFabVisible = MutableStateFlow(false)
    val isFabVisible = _isFabVisible.asStateFlow()

    val quizState = MutableStateFlow<QuizState>(QuizState.Solving)
    val snackbarState = MutableStateFlow<SnackbarState>(SnackbarState.Hide)

    fun onPause() {
        timer.pause()
    }

    fun onResume() {
        timer.start()
    }

    private val quizNumbers: Int = handle[QuizConstants.quizNumbers] ?: DEFAULT_INT

    val numOfTotalQuestions = MutableStateFlow(quizNumbers).asStateFlow()

    private val _numOfSolvedQuestions = MutableStateFlow(0)
    val numOfSolvedQuestions = _numOfSolvedQuestions.asStateFlow()

    val quizType = handle.get<QuizType>(QuizConstants.quizType) ?: QuizType.Accounting

    private val _questions = mutableListOf<Problem>()
    val questions: List<Problem> = _questions

    private val _currentQuestion = MutableStateFlow(Problem())
    val currentQuestion = _currentQuestion.asStateFlow()

    private val _selected = mutableListOf<Int>()
    val selected: List<Int> = _selected

    val timesPerQuestion get() = timer.timesPerQuestion

    val mode = handle.getStateFlow(QuizConstants.mode, ProblemDetailMode.Detail)

    private val _isAnimationShowing = MutableStateFlow(false)
    val isAnimationShowing = _isAnimationShowing.asStateFlow()

    private val _animationInfo = MutableStateFlow<PopScaleAnimationInfo>(PopScaleAnimationInfo.Correct)
    val animationInfo = _animationInfo.asStateFlow()

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
        val currentSelectedIndex = selectedQuestionIndex.value

        if (currentSelectedIndex !in 0..4) {
            snackbarState.value =
                SnackbarState.Show(message = UiText.StringResource(R.string.msg_need_answer))
            quizState.value = QuizState.Solving
            return@launch
        }

        _isAnimationShowing.value = true
        _selected.add(currentSelectedIndex)
        _selectedQuestionIndex.value = -1

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

    private fun onQuiz() {
        _isToolbarTitleVisible.value = true
        _isFabVisible.value = true
    }

    private fun onDetail() {
        handle.get<ProblemModel>(QuizConstants.problemModel)?.toDomain()?.let { question ->
            _currentQuestion.value = question
        }

        _isToolbarTitleVisible.value = false
        _isFabVisible.value = false
    }

    init {
        viewModelScope.launch {
            problemUseCases.getProblems(quizType, quizNumbers, this) {
                _questions.clear()
                _questions.addAll(it)
                onQuizNext()
            }
        }

        when (mode.value) {
            ProblemDetailMode.Quiz -> onQuiz()
            ProblemDetailMode.Detail -> onDetail()
        }
    }
}

sealed class PopScaleAnimationInfo(
    val backgroundColorResId: Int,
    val iconTintColorResId: Int,
    val icon: ImageVector,
) {
    object Correct :
        PopScaleAnimationInfo(R.color.color_on_correct, R.color.daynight_pastel_green, Icons.Default.Check)

    object Incorrect :
        PopScaleAnimationInfo(R.color.color_on_incorrect, R.color.daynight_pastel_red, Icons.Default.Close)
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
