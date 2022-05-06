package com.cpa.cpa_word_problem.feature.quiz.data.repository

import com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.ProblemDao
import com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.WrongProblemDao
import com.cpa.cpa_word_problem.feature.quiz.data.datasource.remote.api.QuizService
import com.cpa.cpa_word_problem.feature.quiz.data.mapper.toDomain
import com.cpa.cpa_word_problem.feature.quiz.data.mapper.toLocalData
import com.cpa.cpa_word_problem.feature.quiz.domain.model.Problem
import com.cpa.cpa_word_problem.feature.quiz.domain.model.QuizType
import com.cpa.cpa_word_problem.feature.quiz.domain.model.WrongProblem
import com.cpa.cpa_word_problem.feature.quiz.domain.repository.QuizRepository
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
) : QuizRepository {

    override suspend fun getLocalProblem(type: QuizType): Problem {
        var problem: Problem

        withContext(Dispatchers.IO) {
            problem = problemDao.get(type)?.toDomain() ?: Problem()
        }

        return problem
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
            try {
                val problems = quizService.getCpaProblems().toDomain().toLocalData()
                problemDao.insert(problems)
            } catch (e: Exception) {
                Timber.e(e)
            }
        }

    override suspend fun getNextExamDate(): String {
        var nextExam = ""
        val now = LocalDate.now().toString()

        withContext(Dispatchers.IO) {
            try {
                quizService.getCpaScheduledDate().toDomain().find { scheduledDate ->
                    now < scheduledDate.date
                }?.let {
                    nextExam = it.date
                }
            } catch (e: Exception) {
                Timber.e(e)
            }
        }

        return nextExam
    }

    override fun getProblemCountByType(type: QuizType): Flow<Int> =
        problemDao.getProblemCountByType(type)

}

