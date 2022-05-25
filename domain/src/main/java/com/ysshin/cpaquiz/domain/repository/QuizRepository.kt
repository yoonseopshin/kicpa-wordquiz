package com.ysshin.cpaquiz.domain.repository

import com.ysshin.cpaquiz.domain.model.Problem
import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.domain.model.WrongProblem
import kotlinx.coroutines.flow.Flow

interface QuizRepository {

    suspend fun getLocalProblem(type: QuizType): Problem

    suspend fun getLocalProblems(type: QuizType, size: Int): List<Problem>

    fun getLocalProblems(): Flow<List<Problem>>

    fun getWrongProblems(): Flow<List<Problem>>

    suspend fun searchProblems(text: String): List<Problem>

    suspend fun insertWrongProblems(wrongProblems: List<WrongProblem>)

    suspend fun deleteWrongProblem(year: Int, pid: Int, type: QuizType)

    suspend fun deleteAllWrongProblems()

    suspend fun syncRemoteProblems()

    suspend fun getNextExamDate(): String

    fun getProblemCountByType(type: QuizType): Flow<Int>

    fun getQuizNumber(): Flow<Int>

    fun getUseTimer(): Flow<Boolean>

    suspend fun setQuizNumber(value: Int)

    suspend fun setUseTimer(value: Boolean)

    suspend fun increaseSolvedQuiz()

    fun getShouldRequestInAppReview(): Flow<Boolean>

    fun getSolvedQuiz(): Flow<Int>

}