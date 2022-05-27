package com.ysshin.cpaquiz.data.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ysshin.cpaquiz.data.database.AppContract.Problem.SOURCE
import com.ysshin.cpaquiz.data.database.AppContract.Problem.TABLE_NAME
import com.ysshin.cpaquiz.domain.model.ProblemSource

object AppMigration {

    private val SQL_10_11 =
            "ALTER TABLE $TABLE_NAME ADD COLUMN $SOURCE TEXT NOT NULL DEFAULT ${ProblemSource.None}"

    private val MIGRATION_10_11: Migration = object : Migration(10, 11) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(SQL_10_11)
        }
    }

    val ALL_MIGRATIONS = arrayOf(MIGRATION_10_11)

}