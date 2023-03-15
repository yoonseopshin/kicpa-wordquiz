package com.ysshin.cpaquiz.data.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ysshin.cpaquiz.data.database.AppContract.Problem.ANSWER
import com.ysshin.cpaquiz.data.database.AppContract.Problem.DESCRIPTION
import com.ysshin.cpaquiz.data.database.AppContract.Problem.PID
import com.ysshin.cpaquiz.data.database.AppContract.Problem.QUESTIONS
import com.ysshin.cpaquiz.data.database.AppContract.Problem.SOURCE
import com.ysshin.cpaquiz.data.database.AppContract.Problem.SUBTYPE
import com.ysshin.cpaquiz.data.database.AppContract.Problem.SUB_DESCRIPTION
import com.ysshin.cpaquiz.data.database.AppContract.Problem.TYPE
import com.ysshin.cpaquiz.data.database.AppContract.Problem.YEAR
import com.ysshin.cpaquiz.data.database.AppContract.WrongProblem.CREATED_AT
import com.ysshin.cpaquiz.domain.model.ProblemSource

object AppMigration {

    private val SQL_10_11 =
        "ALTER TABLE ${AppContract.Problem.TABLE_NAME} ADD COLUMN $SOURCE TEXT NOT NULL DEFAULT ${ProblemSource.CPA}"

    private val MIGRATION_10_11: Migration = object : Migration(10, 11) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(SQL_10_11)
        }
    }

    private const val SQL_11_12 =
        "ALTER TABLE ${AppContract.Problem.TABLE_NAME} ADD COLUMN $SUBTYPE TEXT NOT NULL DEFAULT ''"

    private val MIGRATION_11_12: Migration = object : Migration(11, 12) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(SQL_11_12)
        }
    }

    private val SQL_12_13 = listOf(
        // Create "problems_new" table.
        """
            CREATE TABLE IF NOT EXISTS ${AppContract.Problem.TABLE_NAME}_new 
            ("$PID" INTEGER NOT NULL, 
            "$YEAR" INTEGER NOT NULL, 
            "$DESCRIPTION" TEXT NOT NULL, 
            "$SUB_DESCRIPTION" TEXT NOT NULL, 
            "$QUESTIONS" TEXT NOT NULL, 
            "$ANSWER" INTEGER NOT NULL, 
            "$TYPE" TEXT NOT NULL, 
            "$SOURCE" TEXT NOT NULL, 
            "$SUBTYPE" TEXT NOT NULL, 
            PRIMARY KEY($PID, $YEAR, $TYPE, $SOURCE))
        """.trimIndent(),

        // Copy data from origin "problems" table.
        """
            INSERT INTO ${AppContract.Problem.TABLE_NAME}_new
            ($PID, $YEAR, $DESCRIPTION, $SUB_DESCRIPTION, $QUESTIONS, $ANSWER, $TYPE, $SOURCE, $SUBTYPE)
            SELECT $PID, $YEAR, $DESCRIPTION, $SUB_DESCRIPTION, $QUESTIONS, $ANSWER, $TYPE, $SOURCE, $SUBTYPE
            FROM ${AppContract.Problem.TABLE_NAME}
        """.trimIndent(),

        // Drop origin "problems" table.
        "DROP TABLE ${AppContract.Problem.TABLE_NAME}",

        // Rename "problems_new" to "problems".
        "ALTER TABLE ${AppContract.Problem.TABLE_NAME}_new RENAME TO ${AppContract.Problem.TABLE_NAME}",

        // Create "wrong_problems_new" table.
        """
            CREATE TABLE IF NOT EXISTS ${AppContract.WrongProblem.TABLE_NAME}_new 
            ("$PID" INTEGER NOT NULL, 
            "$YEAR" INTEGER NOT NULL, 
            "$TYPE" TEXT NOT NULL, 
            "$SOURCE" TEXT NOT NULL, 
            "$CREATED_AT" INTEGER NOT NULL, 
            PRIMARY KEY($PID, $YEAR, $TYPE, $SOURCE))
        """.trimIndent(),

        // Since "wrong_problems" table doesn't have "source" column,
        // so add "source" column first with initial value "CPA".
        """
            ALTER TABLE ${AppContract.WrongProblem.TABLE_NAME}
            ADD COLUMN $SOURCE TEXT NOT NULL DEFAULT ${ProblemSource.CPA}
        """.trimIndent(),

        // Copy data from origin "wrong_problems" table.
        """
            INSERT INTO ${AppContract.WrongProblem.TABLE_NAME}_new
            ($PID, $YEAR, $TYPE, $SOURCE, $CREATED_AT)
            SELECT $PID, $YEAR, $TYPE, $SOURCE, $CREATED_AT
            FROM ${AppContract.WrongProblem.TABLE_NAME}
        """.trimIndent(),

        // Drop origin "wrong_problems" table.
        "DROP TABLE ${AppContract.WrongProblem.TABLE_NAME}",

        // Rename "wrong_problems_new" to "wrong_problems"
        "ALTER TABLE ${AppContract.WrongProblem.TABLE_NAME}_new RENAME TO ${AppContract.WrongProblem.TABLE_NAME}",
    )

    private val MIGRATION_12_13: Migration = object : Migration(12, 13) {
        override fun migrate(database: SupportSQLiteDatabase) {
            SQL_12_13.forEach(database::execSQL)
        }
    }

    val ALL_MIGRATIONS = arrayOf(MIGRATION_10_11, MIGRATION_11_12, MIGRATION_12_13)
}
