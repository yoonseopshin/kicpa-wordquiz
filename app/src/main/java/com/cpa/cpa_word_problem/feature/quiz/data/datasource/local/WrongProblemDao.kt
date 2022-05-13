package com.cpa.cpa_word_problem.feature.quiz.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.AppContract.Problem.PID
import com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.AppContract.Problem.TYPE
import com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.AppContract.Problem.YEAR
import com.ysshin.cpaquiz.domain.model.QuizType
import kotlinx.coroutines.flow.Flow

@Dao
interface WrongProblemDao {
    @Query("SELECT * FROM ${AppContract.WrongProblem.TABLE_NAME}")
    fun getAll(): Flow<List<WrongProblemEntity>>

    @Query(
        """
        DELETE FROM
        ${AppContract.WrongProblem.TABLE_NAME} 
        WHERE $YEAR = :year
        AND $PID = :pid
        AND $TYPE = :type
        """
    )
    suspend fun delete(year: Int, pid: Int, type: QuizType)

    @Query("DELETE FROM ${AppContract.WrongProblem.TABLE_NAME}")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(problems: List<WrongProblemEntity>)
}
