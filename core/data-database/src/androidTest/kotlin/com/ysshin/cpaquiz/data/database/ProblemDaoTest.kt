package com.ysshin.cpaquiz.data.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.ysshin.cpaquiz.domain.model.ProblemSource
import com.ysshin.cpaquiz.domain.model.QuizType
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ProblemDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var problemDao: ProblemDao

    @Before
    fun createDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context = context,
            AppDatabase::class.java,
        ).build()
        problemDao = db.problemDao()
    }

    @Test
    fun problemDao_getProblemCountByTypes() = runTest {
        val problemEntities = listOf(
            testProblemResource(pid = 1, year = 2023, type = QuizType.TaxLaw),
            testProblemResource(pid = 2, year = 2023, type = QuizType.TaxLaw),
            testProblemResource(pid = 3, year = 2023, type = QuizType.TaxLaw),
            testProblemResource(pid = 4, year = 2023, type = QuizType.TaxLaw),
            testProblemResource(pid = 5, year = 2023, type = QuizType.TaxLaw),
        )

        problemDao.insert(problemEntities)

        val count = problemDao.getProblemCountByType(QuizType.TaxLaw)
        assertEquals(count, problemEntities.size)
    }
}

private fun testProblemResource(
    pid: Int,
    year: Int,
    description: String = "",
    source: ProblemSource = ProblemSource.CPA,
    type: QuizType = QuizType.Accounting,
    questions: List<String> = listOf("A", "B", "C", "D", "E"),
    answer: Int = 0,
    subtype: String = "",
) = ProblemEntity(
    pid = pid,
    year = year,
    description = description,
    source = source,
    type = type,
    questions = questions,
    answer = answer,
    subtype = subtype,
)
