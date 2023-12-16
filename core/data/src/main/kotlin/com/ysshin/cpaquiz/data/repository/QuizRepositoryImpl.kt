package com.ysshin.cpaquiz.data.repository

import com.ysshin.cpaquiz.core.database.ProblemDao
import com.ysshin.cpaquiz.core.database.ProblemEntity
import com.ysshin.cpaquiz.core.database.WrongProblemDao
import com.ysshin.cpaquiz.core.datastore.QuizDatastoreManager
import com.ysshin.cpaquiz.core.network.api.QuizService
import com.ysshin.cpaquiz.data.mapper.toDomain
import com.ysshin.cpaquiz.data.mapper.toLocalData
import com.ysshin.cpaquiz.domain.model.Problem
import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.domain.model.WrongProblem
import com.ysshin.cpaquiz.domain.repository.QuizRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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

    override fun getTotalProblems() = problemDao.getAll().map { entities ->
        entities.map(ProblemEntity::toDomain)
    }

    override suspend fun getTotalProblems(type: QuizType, subtypes: List<String>): List<Problem> =
        withContext(Dispatchers.IO) {
            runCatching {
                problemDao.get(type, subtypes)
            }
                .onFailure {
                    Timber.e(it)
                }
                .map(List<ProblemEntity>::toDomain)
                .getOrNull() ?: emptyList()
        }

    override suspend fun getTotalProblems(type: QuizType, size: Int): List<Problem> = withContext(Dispatchers.IO) {
        runCatching {
            problemDao.get(type, size)
        }
            .onFailure {
                Timber.e(it)
            }
            .map(List<ProblemEntity>::toDomain)
            .getOrNull() ?: emptyList()
    }

    override fun getWrongProblems(): Flow<List<Problem>> = wrongProblemDao.getAll().map { entities ->
        entities.map { wrongProblem ->
            problemDao.get(
                wrongProblem.year,
                wrongProblem.pid,
                wrongProblem.type,
                wrongProblem.source,
            )
                .toDomain()
        }
    }

    override suspend fun searchProblems(text: String) =
        if (text.isBlank()) emptyList() else problemDao.search(text).map(ProblemEntity::toDomain)

    override suspend fun upsertWrongProblems(wrongProblems: List<WrongProblem>) = withContext(Dispatchers.IO) {
        wrongProblemDao.upsert(wrongProblems.toLocalData())
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

    override suspend fun syncRemoteProblems(): Unit = withContext(Dispatchers.IO) {
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
        runCatching {
            quizDataStoreManager.setTimer(value)
        }
            .onFailure {
                Timber.e(it)
            }
    }

    override suspend fun increaseSolvedQuiz() {
        runCatching {
            quizDataStoreManager.increaseSolvedQuiz()
        }
            .onFailure {
                Timber.e(it)
            }
    }

    override fun getShouldRequestInAppReview() = quizDataStoreManager.shouldRequestInAppReview

    override fun getShouldShowInterstitialAd() = quizDataStoreManager.shouldShowInterstitialAd

    override fun getSolvedQuiz() = quizDataStoreManager.solvedQuiz
}
