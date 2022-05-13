package com.ysshin.cpaquiz.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ysshin.cpaquiz.data.database.AppMigration.ALL_MIGRATIONS
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
@Database(
    entities = [ProblemEntity::class, WrongProblemEntity::class],
    version = 11,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun problemDao(): ProblemDao
    abstract fun wrongProblemDao(): WrongProblemDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, AppContract.DATABASE_NAME)
                .addMigrations(*ALL_MIGRATIONS)
                .fallbackToDestructiveMigration()
                .build()
    }
}