package com.cpa.cpa_word_problem.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.cpa.cpa_word_problem.data.ProblemContract.ProblemEntity

@Dao
interface ProblemDao {
    @Query("SELECT * FROM ${ProblemEntity.TABLE_NAME}")
    fun getAll(): LiveData<List<ProblemData>?>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(problem: ProblemData)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(problems: List<ProblemData>)

    @Delete
    fun delete(problem: ProblemData)

    @Query("DELETE FROM ${ProblemEntity.TABLE_NAME}")
    fun deleteAll()
}