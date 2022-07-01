package com.ysshin.cpaquiz.domain.repository

import com.ysshin.cpaquiz.domain.model.Problem
import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.domain.model.WrongProblem
import kotlinx.coroutines.flow.Flow

interface QuizRepository {

    suspend fun getLocalProblems(type: QuizType, size: Int): List<Problem>

    suspend fun getLocalProblems(years: List<Int>, types: List<QuizType>): List<Problem>

    fun getWrongProblems(): Flow<List<Problem>>

    suspend fun searchProblems(text: String): List<Problem>

    suspend fun insertWrongProblems(wrongProblems: List<WrongProblem>)

    suspend fun deleteWrongProblem(year: Int, pid: Int, type: QuizType): Boolean

    suspend fun deleteAllWrongProblems(): Boolean

    suspend fun syncRemoteProblems()

    fun getNextExamDate(): Flow<String>

    fun getProblemCountByType(type: QuizType): Flow<Int>

    fun getQuizNumber(): Flow<Int>

    fun getUseTimer(): Flow<Boolean>

    suspend fun setQuizNumber(value: Int)

    suspend fun setUseTimer(value: Boolean)

    suspend fun increaseSolvedQuiz()

    fun getShouldRequestInAppReview(): Flow<Boolean>

    fun getSolvedQuiz(): Flow<Int>
}
