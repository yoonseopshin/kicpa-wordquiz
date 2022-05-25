package com.ysshin.cpaquiz.feature.quiz.presentation.screen.quiz

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
