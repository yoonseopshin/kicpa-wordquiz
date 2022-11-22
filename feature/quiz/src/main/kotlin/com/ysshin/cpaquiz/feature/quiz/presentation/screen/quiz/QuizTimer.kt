package com.ysshin.cpaquiz.feature.quiz.presentation.screen.quiz

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

interface Timer {
    val elapsedTime: StateFlow<Long>
    fun start()
    fun record()
    fun pause()
}

class QuizTimer(private val scope: CoroutineScope) : Timer {

    private var timerJob: Job? = null
    private var timeMillis = 0L
    private var isActive: Boolean = false

    private val laps = mutableListOf<Long>()
    private val lastLapTime get() = if (laps.isEmpty()) 0L else laps.last()
    val timesPerQuestion get() = laps.mapIndexed { index, value ->
        val prevValue = if (index > 0) laps[index - 1] else 0
        value - prevValue
    }

    override val elapsedTime = MutableStateFlow(0L)

    override fun start() {
        if (isActive) return

        Timber.d("Timer start")
        timerJob = scope.launch {
            isActive = true
            var lastTimeMillis = System.currentTimeMillis()

            while (isActive) {
                delay(200L)
                Timber.d("Timer tick")
                timeMillis = timeMillis + System.currentTimeMillis() - lastTimeMillis
                lastTimeMillis = System.currentTimeMillis()
                elapsedTime.update { timeMillis - lastLapTime }
            }
        }
    }

    override fun record() {
        Timber.d("Timer record")
        laps.add(timeMillis)
    }

    override fun pause() {
        Timber.d("Timer pause")
        isActive = false
        timerJob?.cancel()
        timerJob = null
    }
}
