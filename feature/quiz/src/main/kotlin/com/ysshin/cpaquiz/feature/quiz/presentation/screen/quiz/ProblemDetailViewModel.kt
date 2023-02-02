package com.ysshin.cpaquiz.feature.quiz.presentation.screen.quiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ysshin.cpaquiz.core.android.base.BaseViewModel
import com.ysshin.cpaquiz.core.base.Action
import com.ysshin.cpaquiz.core.base.DEFAULT_INT
import com.ysshin.cpaquiz.domain.model.Problem
import com.ysshin.cpaquiz.domain.model.ProblemDetailMode
import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.domain.usecase.problem.ProblemUseCases
import com.ysshin.cpaquiz.domain.usecase.quiz.QuizUseCases
import com.ysshin.cpaquiz.feature.quiz.presentation.util.QuizConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class ProblemDetailViewModel @Inject constructor(
    private val problemUseCases: ProblemUseCases,
    private val quizUseCases: QuizUseCases,
    private val handle: SavedStateHandle,
) : BaseViewModel() {

    val useTimer = MutableStateFlow(false)
    var timerJob: Job? = null
    var isTimerActive = false
    var lastTimestamp = 0L
    val timeMillis = MutableStateFlow(0L)
    val lastLapTime = MutableStateFlow(0L)
    private val laps = mutableListOf<Long>()
    val timesPerProblem: List<Long>
        get() {
            val result = mutableListOf<Long>()

            for (i in 0 until laps.size - 1) {
                result.add(laps[i + 1] - laps[i])
            }

            return result
        }

    val mode = MutableStateFlow(ProblemDetailMode.Detail)
    val problem = MutableStateFlow(Problem())

    private val _problems = mutableListOf<Problem>()
    val problems: List<Problem> get() = _problems

    private val _selected = mutableListOf<Int>()
    val selected: List<Int> get() = _selected

    val quizType = MutableStateFlow(QuizType.Accounting)

    val totalProblemNumbers = handle.getStateFlow(QuizConstants.quizNumbers, DEFAULT_INT)

    private val _solvedProblemNumbers = MutableStateFlow(DEFAULT_INT)
    val solvedProblemNumbers = _solvedProblemNumbers.asStateFlow()

    private val _quizEvent = MutableSharedFlow<QuizEvent>()
    val quizEvent = _quizEvent.asSharedFlow()

    fun start(quizNumbers: Int) = viewModelScope.launch {
        withContext(Dispatchers.Main) {
            problemUseCases.getProblems(quizType.value, quizNumbers, this) {
                _problems.addAll(it)
            }
        }
        _quizEvent.emit(QuizEvent.Started)
    }

    fun onStart(onStartResult: Action) {
        startTimer()
        onStartResult()
    }

    fun onPause() {
        pauseTimer()
    }

    fun onResume() {
        startTimer()
    }

    private fun startTimer() {
        if (useTimer.value.not()) return

        timerJob = viewModelScope.launch {
            lastTimestamp = System.currentTimeMillis()
            isTimerActive = true

            while (isTimerActive) {
                delay(10L)
                timeMillis.update { it + System.currentTimeMillis() - lastTimestamp }
                lastTimestamp = System.currentTimeMillis()
            }
        }
    }

    fun resume() {
        viewModelScope.launch {
            _quizEvent.emit(QuizEvent.Resumed)
        }
    }

    fun pause() {
        viewModelScope.launch {
            _quizEvent.emit(QuizEvent.Paused)
        }
    }

    private fun pauseTimer() {
        if (useTimer.value.not()) return

        isTimerActive = false
    }

    private fun stopTimer() {
        if (useTimer.value.not()) return

        timerJob?.cancel()
    }

    fun calculate() {
        viewModelScope.launch {
            _quizEvent.emit(QuizEvent.Calculating)
        }
    }

    fun onCalculating(selectedIndexByUser: Int) {
        viewModelScope.launch {
            _selected.add(selectedIndexByUser)
            if (problem.value.answer == selectedIndexByUser) {
                _quizEvent.emit(QuizEvent.Correct)
            } else {
                _quizEvent.emit(QuizEvent.Incorrect)
            }
        }
    }

    fun next() {
        viewModelScope.launch {
            _quizEvent.emit(QuizEvent.Next)
            laps.add(timeMillis.value)
            lastLapTime.update { laps.last() }
        }
    }

    fun onNext(onNextResult: Action) {
        viewModelScope.launch {
            if (totalProblemNumbers.value > solvedProblemNumbers.value) {
                _solvedProblemNumbers.update { solved ->
                    problem.value = _problems[solved]
                    solved + 1
                }
                onNextResult()
            } else {
                _quizEvent.emit(QuizEvent.Ended)
            }
        }
    }

    fun onEnd(onEndResult: Action) {
        stopTimer()

        viewModelScope.launch {
            quizUseCases.increaseSolvedQuiz()
        }

        onEndResult()
    }
}

sealed class QuizEvent {
    object Started : QuizEvent()
    object Paused : QuizEvent()
    object Resumed : QuizEvent()
    object Calculating : QuizEvent()
    object Correct : QuizEvent()
    object Incorrect : QuizEvent()
    object Next : QuizEvent()
    object Ended : QuizEvent()
}
