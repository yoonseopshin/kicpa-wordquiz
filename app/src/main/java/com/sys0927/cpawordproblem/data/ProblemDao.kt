package com.sys0927.cpawordproblem.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sys0927.cpawordproblem.data.ProblemContract.ProblemEntity

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