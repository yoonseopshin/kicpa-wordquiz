package com.ysshin.cpaquiz.data.repository

import com.ysshin.cpaquiz.data.database.ProblemDao
import com.ysshin.cpaquiz.data.database.WrongProblemDao
import com.ysshin.cpaquiz.data.datastore.QuizDatastoreManager
import com.ysshin.cpaquiz.data.mapper.toDomain
import com.ysshin.cpaquiz.data.mapper.toLocalData
import com.ysshin.cpaquiz.data.network.api.QuizService
import com.ysshin.cpaquiz.domain.model.Problem
import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.domain.model.WrongProblem
import com.ysshin.cpaquiz.domain.repository.QuizRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import timber.log.Timber
import java.time.LocalDate
import javax.inject.Inject

@ExperimentalSerializationApi
class QuizRepositoryImpl @Inject constructor(
    private val quizService: QuizService,
    private val problemDao: ProblemDao,
    private val wrongProblemDao: WrongProblemDao,
    private val quizDataStoreManager: QuizDatastoreManager,
) : QuizRepository {

    override suspend fun getLocalProblem(type: QuizType): Problem =
        withContext(Dispatchers.IO) {
            runCatching {
                problemDao.get(type)
            }.map {
                it?.toDomain()
            }.getOrNull() ?: Problem()
        }

    override suspend fun getLocalProblems(type: QuizType, size: Int): List<Problem> =
        withContext(Dispatchers.IO) {
            runCatching {
                problemDao.get(type, size)
            }.map {
                it.toDomain()
            }.getOrNull() ?: emptyList()
        }

    override fun getLocalProblems(): Flow<List<Problem>> = problemDao.getAll().map { it.toDomain() }

    override fun getWrongProblems(): Flow<List<Problem>> =
        wrongProblemDao.getAll().map { wrongProblemEntities ->
            mutableListOf<Problem>().let { problems ->
                for (wrongProblem in wrongProblemEntities) {
                    problems.add(
                        problemDao.get(
                            wrongProblem.year,
                            wrongProblem.pid,
                            wrongProblem.type
                        ).toDomain()
                    )
                }
                problems
            }
        }

    override suspend fun searchProblems(text: String) =
        problemDao.search(text).map { it.toDomain() }

    override suspend fun insertWrongProblems(wrongProblems: List<WrongProblem>) =
        withContext(Dispatchers.IO) {
            wrongProblemDao.insert(wrongProblems.toLocalData())
        }

    override suspend fun deleteWrongProblem(year: Int, pid: Int, type: QuizType) =
        withContext(Dispatchers.IO) {
            wrongProblemDao.delete(year, pid, type)
        }

    override suspend fun deleteAllWrongProblems() =
        withContext(Dispatchers.IO) {
            wrongProblemDao.deleteAll()
        }

    override suspend fun syncRemoteProblems() =
        withContext(Dispatchers.IO) {
            runCatching {
                quizService.getCpaProblems()
            }.map { problems ->
                problems.toDomain().toLocalData()
            }.onSuccess { problems ->
                problemDao.insert(problems)
            }.onFailure { throwable ->
                Timber.e(throwable)
            }.fold(
                onSuccess = { },
                onFailure = { }
            )
        }

    override suspend fun getNextExamDate() =
        withContext(Dispatchers.IO) {
            var nextExam = ""

            runCatching {
                quizService.getCpaScheduledDate()
            }.map { scheduledDates ->
                scheduledDates.toDomain()
            }.onSuccess { scheduledDates ->
                scheduledDates.find { scheduledDate ->
                    LocalDate.now().toString() < scheduledDate.date
                }?.run {
                    nextExam = date
                }
            }.onFailure { throwable ->
                Timber.e(throwable)
            }.fold(
                onSuccess = { nextExam },
                onFailure = { nextExam }
            )
        }

    override fun getProblemCountByType(type: QuizType): Flow<Int> =
        problemDao.getProblemCountByType(type)

    override fun getQuizNumber() = quizDataStoreManager.quizNumber

    override fun getUseTimer() = quizDataStoreManager.useTimer

    override suspend fun setQuizNumber(value: Int) {
        quizDataStoreManager.setQuizNumber(value)
    }

    override suspend fun setUseTimer(value: Boolean) {
        quizDataStoreManager.setTimer(value)
    }

    override suspend fun increaseSolvedQuiz() {
        quizDataStoreManager.increaseSolvedQuiz()
    }

    override fun getShouldRequestInAppReview() = quizDataStoreManager.shouldRequestInAppReview

    override fun getSolvedQuiz() = quizDataStoreManager.solvedQuiz

}

