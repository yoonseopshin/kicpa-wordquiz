package com.cpa.cpa_word_problem.data.model.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProblemDao {
    @Query("SELECT * FROM ${AppContract.ProblemEntity.TABLE_NAME}")
    fun getAll(): Flow<List<Problem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg problem: Problem)
}
