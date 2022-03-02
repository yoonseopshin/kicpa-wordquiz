package com.cpa.cpa_word_problem.feature.quiz.presentation.screen.quiz

import androidx.lifecycle.viewModelScope
import com.cpa.cpa_word_problem.base.BaseViewModel
import com.cpa.cpa_word_problem.feature.quiz.domain.model.Problem
import com.cpa.cpa_word_problem.feature.quiz.domain.model.QuizType
import com.cpa.cpa_word_problem.feature.quiz.domain.model.isValid
import com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem.ProblemUseCases
import com.cpa.cpa_word_problem.utils.Action
import com.cpa.cpa_word_problem.utils.DEFAULT_INT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProblemDetailViewModel @Inject constructor(
    private val problemUseCases: ProblemUseCases,
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

    private val _totalProblemNumbers = MutableStateFlow(DEFAULT_INT)
    val totalProblemNumbers = _totalProblemNumbers.asStateFlow()

    private val _solvedProblemNumbers = MutableStateFlow(DEFAULT_INT)
    val solvedProblemNumbers = _solvedProblemNumbers.asStateFlow()

    private val _quizEvent = MutableSharedFlow<QuizEvent>()
    val quizEvent = _quizEvent.asSharedFlow()

    fun start(quizNumbers: Int) {
        if (_totalProblemNumbers.value == quizNumbers) return

        viewModelScope.launch {
            _totalProblemNumbers.value = quizNumbers
            _quizEvent.emit(QuizEvent.Started)
        }
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
                problemUseCases.getProblem(quizType.value, this) { newProblem ->
                    if (newProblem.isValid()) {
                        problem.value = newProblem
                        _problems.add(newProblem)
                        _solvedProblemNumbers.update { solved -> solved + 1 }
                        onNextResult()
                    }
                }
            } else {
                _quizEvent.emit(QuizEvent.Ended)
            }
        }
    }

    fun onEnd(onEndResult: Action) {
        stopTimer()
        onEndResult()
    }

}