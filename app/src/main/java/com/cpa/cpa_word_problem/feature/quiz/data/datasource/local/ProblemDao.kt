package com.cpa.cpa_word_problem.feature.quiz.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.AppContract.Problem.DESCRIPTION
import com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.AppContract.Problem.PID
import com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.AppContract.Problem.QUESTIONS
import com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.AppContract.Problem.SUB_DESCRIPTION
import com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.AppContract.Problem.TYPE
import com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.AppContract.Problem.YEAR
import com.cpa.cpa_word_problem.feature.quiz.domain.model.QuizType
import kotlinx.coroutines.flow.Flow

@Dao
interface ProblemDao {
    @Query("SELECT * FROM ${AppContract.Problem.TABLE_NAME}")
    fun getAll(): Flow<List<ProblemEntity>>

    @Query(
        """
        SELECT * 
        FROM ${AppContract.Problem.TABLE_NAME} 
        WHERE $TYPE = :type 
        ORDER BY RANDOM() 
        LIMIT 1"""
    )
    fun get(type: QuizType): ProblemEntity?

    @Query(
        """
            SELECT *
            FROM ${AppContract.Problem.TABLE_NAME}
            WHERE $TYPE = :type
            AND $YEAR= :year
            AND $PID= :pid
            LIMIT 1
        """
    )
    fun get(year: Int, pid: Int, type: QuizType): ProblemEntity

    @Query(
        """
            SELECT COUNT(*)
            FROM ${AppContract.Problem.TABLE_NAME}
            WHERE $TYPE = :type
            """
    )
    fun getProblemCountByType(type: QuizType): Flow<Int>

    @Query(
        """
        SELECT *
        FROM ${AppContract.Problem.TABLE_NAME}
        WHERE $DESCRIPTION LIKE '%' || :text || '%'
        OR $SUB_DESCRIPTION LIKE '%' || :text || '%'
        OR $QUESTIONS LIKE '%' || :text || '%'
        """
    )
    suspend fun search(text: String): List<ProblemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(problems: List<ProblemEntity>)
}
