package com.cpa.cpa_word_problem.feature.quiz.presentation.screen.quiz

sealed class QuizState {
    object Started : QuizState()
    object Paused : QuizState()
    object Resumed : QuizState()
    object Calculating : QuizState()
    object Correct : QuizState()
    object Incorrect : QuizState()
    object Next : QuizState()
    object Ended : QuizState()
}
