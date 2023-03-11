package com.ysshin.cpaquiz.domain.repository

import com.ysshin.cpaquiz.domain.model.Problem
import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.domain.model.WrongProblem
import kotlinx.coroutines.flow.Flow

interface QuizRepository {

    fun getTotalProblems(): Flow<List<Problem>>

    suspend fun getTotalProblems(type: QuizType, subtypes: List<String>): List<Problem>

    suspend fun getTotalProblems(type: QuizType, size: Int): List<Problem>

    fun getWrongProblems(): Flow<List<Problem>>

    suspend fun searchProblems(text: String): List<Problem>

    suspend fun insertWrongProblems(wrongProblems: List<WrongProblem>)

    suspend fun deleteWrongProblem(year: Int, pid: Int, type: QuizType): Boolean

    suspend fun deleteAllWrongProblems(): Boolean

    suspend fun syncRemoteProblems()

    fun getNextExamDate(): Flow<String>

    suspend fun getProblemCountByType(type: QuizType): Int

    suspend fun getSubtypesByQuizType(type: QuizType): List<String>

    fun getQuizNumber(): Flow<Int>

    fun getUseTimer(): Flow<Boolean>

    suspend fun setQuizNumber(value: Int)

    suspend fun setUseTimer(value: Boolean)

    suspend fun increaseSolvedQuiz()

    fun getShouldRequestInAppReview(): Flow<Boolean>

    fun getSolvedQuiz(): Flow<Int>
}
