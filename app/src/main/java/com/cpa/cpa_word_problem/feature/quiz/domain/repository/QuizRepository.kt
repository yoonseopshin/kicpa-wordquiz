package com.cpa.cpa_word_problem.feature.quiz.domain.repository

import com.cpa.cpa_word_problem.feature.quiz.domain.model.Problem
import com.cpa.cpa_word_problem.feature.quiz.domain.model.QuizType
import com.cpa.cpa_word_problem.feature.quiz.domain.model.WrongProblem
import kotlinx.coroutines.flow.Flow

interface QuizRepository {

    suspend fun getLocalProblem(year: Int, pid: Int, type: QuizType): Problem

    suspend fun getLocalProblem(type: QuizType): Problem

    fun getLocalProblems(): Flow<List<Problem>>

    fun getWrongProblems(): Flow<List<Problem>>

    suspend fun searchProblems(text: String): List<Problem>

    suspend fun insertWrongProblems(wrongProblems: List<WrongProblem>)

    suspend fun deleteWrongProblem(year: Int, pid: Int, type: QuizType)

    suspend fun deleteAllWrongProblems()

    suspend fun syncRemoteProblems()

    suspend fun getNextExamDate(): String

}