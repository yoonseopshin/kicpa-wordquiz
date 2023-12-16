package com.ysshin.cpaquiz.core.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.ysshin.cpaquiz.core.database.AppContract.Problem.PID
import com.ysshin.cpaquiz.core.database.AppContract.Problem.TYPE
import com.ysshin.cpaquiz.core.database.AppContract.Problem.YEAR
import com.ysshin.cpaquiz.domain.model.QuizType
import kotlinx.coroutines.flow.Flow

@Dao
interface WrongProblemDao {
    @Query("SELECT *FROM ${AppContract.WrongProblem.TABLE_NAME}")
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

    @Upsert
    suspend fun upsert(problems: List<WrongProblemEntity>)
}
