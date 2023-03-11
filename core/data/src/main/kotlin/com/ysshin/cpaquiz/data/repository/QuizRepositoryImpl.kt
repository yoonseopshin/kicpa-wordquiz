package com.ysshin.cpaquiz.data.repository

import com.ysshin.cpaquiz.data.database.ProblemDao
import com.ysshin.cpaquiz.data.database.ProblemEntity
import com.ysshin.cpaquiz.data.database.WrongProblemDao
import com.ysshin.cpaquiz.data.datastore.QuizDatastoreManager
import com.ysshin.cpaquiz.data.mapper.toDomain
import com.ysshin.cpaquiz.data.mapper.toLocalData
import com.ysshin.cpaquiz.data.network.api.QuizService
import com.ysshin.cpaquiz.domain.model.Problem
import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.domain.model.WrongProblem
import com.ysshin.cpaquiz.domain.repository.QuizRepository
import java.time.LocalDate
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import timber.log.Timber

@ExperimentalSerializationApi
class QuizRepositoryImpl @Inject constructor(
    private val quizService: QuizService,
    private val problemDao: ProblemDao,
    private val wrongProblemDao: WrongProblemDao,
    private val quizDataStoreManager: QuizDatastoreManager,
) : QuizRepository {

    override fun getTotalProblems() = problemDao.getAll().map { entities ->
        entities.map(ProblemEntity::toDomain)
    }

    override suspend fun getTotalProblems(type: QuizType, subtypes: List<String>): List<Problem> =
        withContext(Dispatchers.IO) {
            runCatching {
                problemDao.get(type, subtypes)
            }.map(List<ProblemEntity>::toDomain)
                .getOrNull() ?: emptyList()
        }

    override suspend fun getTotalProblems(type: QuizType, size: Int): List<Problem> =
        withContext(Dispatchers.IO) {
            runCatching {
                problemDao.get(type, size)
            }.map(List<ProblemEntity>::toDomain)
                .getOrNull() ?: emptyList()
        }

    override fun getWrongProblems(): Flow<List<Problem>> =
        wrongProblemDao.getAll().map { entities ->
            entities.map { wrongProblem ->
                problemDao.get(wrongProblem.year, wrongProblem.pid, wrongProblem.type).toDomain()
            }
        }

    override suspend fun searchProblems(text: String) =
        if (text.isBlank()) emptyList() else problemDao.search(text).map(ProblemEntity::toDomain)

    override suspend fun insertWrongProblems(wrongProblems: List<WrongProblem>) =
        withContext(Dispatchers.IO) {
            wrongProblemDao.insert(wrongProblems.toLocalData())
        }

    override suspend fun deleteWrongProblem(year: Int, pid: Int, type: QuizType) = runCatching {
        withContext(Dispatchers.IO) {
            wrongProblemDao.delete(year, pid, type)
        }
    }.isSuccess

    override suspend fun deleteAllWrongProblems() = runCatching {
        withContext(Dispatchers.IO) {
            wrongProblemDao.deleteAll()
        }
    }.isSuccess

    override suspend fun syncRemoteProblems(): Unit =
        withContext(Dispatchers.IO) {
            runCatching {
                quizService.getCpaProblems()
            }.map { problems ->
                problems.toDomain().toLocalData()
            }.onSuccess { problems ->
                problemDao.insert(problems)
            }.onFailure { throwable ->
                Timber.e(throwable)
            }
        }

    override fun getNextExamDate() = flow {
        runCatching {
            quizService.getCpaScheduledDate()
        }.map { scheduledDates ->
            scheduledDates.toDomain()
        }.onSuccess { scheduledDates ->
            scheduledDates.find { scheduledDate ->
                LocalDate.now().toString() < scheduledDate.date
            }?.run {
                date
            }?.let { date ->
                emit(date)
            }
        }.onFailure { throwable ->
            Timber.e(throwable)
        }
    }

    override suspend fun getProblemCountByType(type: QuizType) = withContext(Dispatchers.IO) {
        problemDao.getProblemCountByType(type)
    }

    override suspend fun getSubtypesByQuizType(type: QuizType) = withContext(Dispatchers.IO) {
        problemDao.getSubtypesByQuizType(type)
    }

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
