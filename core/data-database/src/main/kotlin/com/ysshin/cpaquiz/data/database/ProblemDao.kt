package com.ysshin.cpaquiz.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ysshin.cpaquiz.data.database.AppContract.Problem.DESCRIPTION
import com.ysshin.cpaquiz.data.database.AppContract.Problem.PID
import com.ysshin.cpaquiz.data.database.AppContract.Problem.QUESTIONS
import com.ysshin.cpaquiz.data.database.AppContract.Problem.SUB_DESCRIPTION
import com.ysshin.cpaquiz.data.database.AppContract.Problem.TYPE
import com.ysshin.cpaquiz.data.database.AppContract.Problem.YEAR
import com.ysshin.cpaquiz.domain.model.QuizType
import kotlinx.coroutines.flow.Flow

@Dao
interface ProblemDao {

    @Query(
        """
        SELECT * 
        FROM ${AppContract.Problem.TABLE_NAME}
        """
    )
    fun getAll(): Flow<List<ProblemEntity>>

    @Query(
        """
        SELECT DISTINCT * 
        FROM ${AppContract.Problem.TABLE_NAME} 
        WHERE $TYPE = :type 
        ORDER BY RANDOM() 
        LIMIT :size"""
    )
    fun get(type: QuizType, size: Int): List<ProblemEntity>

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
    suspend fun get(year: Int, pid: Int, type: QuizType): ProblemEntity

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
        OR $PID LIKE '%' || :text || '%'
        """
    )
    suspend fun search(text: String): List<ProblemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(problems: List<ProblemEntity>)
}