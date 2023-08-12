package com.ysshin.cpaquiz.domain.usecase.quiz

data class QuizUseCases(
    val getNextExamDate: GetNextExamDate,
    val getQuizNumber: GetQuizNumber,
    val getUseTimer: GetUseTimer,
    val setQuizNumber: SetQuizNumber,
    val setUseTimer: SetUseTimer,
    val increaseSolvedQuiz: IncreaseSolvedQuiz,
    val getSolvedQuiz: GetSolvedQuiz,
)
