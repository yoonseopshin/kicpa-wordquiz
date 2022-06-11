package com.ysshin.cpaquiz.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ysshin.cpaquiz.data.database.AppContract.Problem.PID
import com.ysshin.cpaquiz.data.database.AppContract.Problem.TYPE
import com.ysshin.cpaquiz.data.database.AppContract.Problem.YEAR
import com.ysshin.cpaquiz.domain.model.QuizType
import kotlinx.coroutines.flow.Flow

@Dao
interface WrongProblemDao {
    @Query("""
        SELECT * 
        FROM ${AppContract.WrongProblem.TABLE_NAME}
        WHERE $YEAR IN (:years)
        AND $TYPE IN (:types)
        """)
    suspend fun getAll(years: List<Int>, types: List<QuizType>): List<WrongProblemEntity>

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
