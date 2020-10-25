package com.cpa.cpa_word_problem.data

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object AppMigration {
    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            val sql = "ALTER TABLE ${ProblemContract.ProblemEntity.TABLE_NAME} " +
                    "ADD COLUMN ${ProblemContract.ProblemEntity.TYPE} TEXT " +
                    "DEFAULT ${ProblemType.Accounting} NOT NULL"
            database.execSQL(sql)
        }
    }
}