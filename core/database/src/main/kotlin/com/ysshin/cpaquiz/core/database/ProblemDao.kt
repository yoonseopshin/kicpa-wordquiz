package com.ysshin.cpaquiz.core.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.ysshin.cpaquiz.core.database.AppContract.Problem.DESCRIPTION
import com.ysshin.cpaquiz.core.database.AppContract.Problem.PID
import com.ysshin.cpaquiz.core.database.AppContract.Problem.QUESTIONS
import com.ysshin.cpaquiz.core.database.AppContract.Problem.SOURCE
import com.ysshin.cpaquiz.core.database.AppContract.Problem.SUBTYPE
import com.ysshin.cpaquiz.core.database.AppContract.Problem.SUB_DESCRIPTION
import com.ysshin.cpaquiz.core.database.AppContract.Problem.TYPE
import com.ysshin.cpaquiz.core.database.AppContract.Problem.YEAR
import com.ysshin.cpaquiz.domain.model.ProblemSource
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
        AND $SUBTYPE in (:subtypes)
        ORDER BY RANDOM()
        """
    )
    suspend fun get(type: QuizType, subtypes: List<String>): List<ProblemEntity>

    @Query(
        """
        SELECT DISTINCT * 
        FROM ${AppContract.Problem.TABLE_NAME} 
        WHERE $TYPE = :type 
        ORDER BY RANDOM() 
        LIMIT :size"""
    )
    suspend fun get(type: QuizType, size: Int): List<ProblemEntity>

    @Query(
        """
            SELECT *
            FROM ${AppContract.Problem.TABLE_NAME}
            WHERE $TYPE = :type
            AND $YEAR= :year
            AND $PID= :pid
            AND $SOURCE= :source
            LIMIT 1
        """
    )
    suspend fun get(year: Int, pid: Int, type: QuizType, source: ProblemSource): ProblemEntity

    @Query(
        """
            SELECT COUNT(*)
            FROM ${AppContract.Problem.TABLE_NAME}
            WHERE $TYPE = :type
            """
    )
    suspend fun getProblemCountByType(type: QuizType): Int

    @Query(
        """
        SELECT DISTINCT $SUBTYPE
        FROM ${AppContract.Problem.TABLE_NAME}
        WHERE $TYPE = :type 
    """
    )
    suspend fun getSubtypesByQuizType(type: QuizType): List<String>

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

    @Upsert
    suspend fun insert(problems: List<ProblemEntity>)
}
