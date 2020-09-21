package com.cpa.cpa_word_problem.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ProblemData::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun problemDao(): ProblemDao
}