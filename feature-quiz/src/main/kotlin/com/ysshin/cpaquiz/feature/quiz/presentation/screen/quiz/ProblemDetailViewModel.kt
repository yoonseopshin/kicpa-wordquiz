package com.ysshin.cpaquiz.feature.quiz.presentation.screen.quiz

import androidx.lifecycle.viewModelScope
import com.ysshin.cpaquiz.domain.model.Problem
import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.domain.model.isValid
import com.ysshin.cpaquiz.domain.usecase.problem.ProblemUseCases
import com.ysshin.cpaquiz.domain.usecase.quiz.QuizUseCases
import com.ysshin.cpaquiz.shared.android.base.BaseViewModel
import com.ysshin.cpaquiz.shared.base.Action
import com.ysshin.cpaquiz.shared.base.DEFAULT_INT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProblemDetailViewModel @Inject constructor(
    private val problemUseCases: ProblemUseCases,
    private val quizUseCases: QuizUseCases
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

    private val _quizState = MutableSharedFlow<QuizState>()
    val quizState = _quizState.asSharedFlow()

    fun start(quizNumbers: Int) {
        if (_totalProblemNumbers.value == quizNumbers) return

        viewModelScope.launch {
            _totalProblemNumbers.value = quizNumbers
            _quizState.emit(QuizState.Started)
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
            _quizState.emit(QuizState.Resumed)
        }
    }

    fun pause() {
        viewModelScope.launch {
            _quizState.emit(QuizState.Paused)
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
            _quizState.emit(QuizState.Calculating)
        }
    }

    fun onCalculating(selectedIndexByUser: Int) {
        viewModelScope.launch {
            _selected.add(selectedIndexByUser)
            if (problem.value.answer == selectedIndexByUser) {
                _quizState.emit(QuizState.Correct)
            } else {
                _quizState.emit(QuizState.Incorrect)
            }
        }
    }

    fun next() {
        viewModelScope.launch {
            _quizState.emit(QuizState.Next)
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
                _quizState.emit(QuizState.Ended)
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