package com.example.cpa_word_problem

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class WrongProblemDBHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "wrong_problem.db"
        const val TABLE_NAME = "wrong_problems"
        const val PID = "pid"
        const val YEAR = "year"
        const val DESCRIPTION = "description"
        const val P1 = "p1"
        const val P2 = "p2"
        const val P3 = "p3"
        const val P4 = "p4"
        const val P5 = "p5"
        const val ANSWER = "answer"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "create table if not exists $TABLE_NAME (" +
                "$PID integer, " +
                "$YEAR integer, " +
                "$DESCRIPTION varchar(120), " +
                "$P1 varchar(300)," +
                "$P2 varchar(300), " +
                "$P3 varchar(300), " +
                "$P4 varchar(300), " +
                "$P5 varchar(300), " +
                "$ANSWER integer, " +
                "primary key($PID, $YEAR))"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTable = "drop table if exists $TABLE_NAME"
        db?.execSQL(dropTable)
        onCreate(db)
    }

    fun save(cache : LinkedHashSet<AccountingData>) {
        val db = this.writableDatabase
        db.beginTransaction()
        cache.forEach {
            val contentValue = ContentValues()
            contentValue.put(PID, it.pid)
            contentValue.put(YEAR, it.year)
            contentValue.put(DESCRIPTION, it.description)
            contentValue.put(P1, it.p1)
            contentValue.put(P2, it.p2)
            contentValue.put(P3, it.p3)
            contentValue.put(P4, it.p4)
            contentValue.put(P5, it.p5)
            contentValue.put(ANSWER, it.answer)
            db.insert(TABLE_NAME, null, contentValue)
        }
        db.setTransactionSuccessful()
        db.endTransaction()
    }

    fun fetch() : ArrayList<AccountingData> {
        val db = this.readableDatabase
        val fetchAll = "select * from $TABLE_NAME"
        val cursor = db.rawQuery(fetchAll, null)
        val wrongProblemList = arrayListOf<AccountingData>()
        while (cursor.moveToNext()) {
            val pid = cursor.getInt(0)
            val year = cursor.getInt(1)
            val description = cursor.getString(2)
            val p1 = cursor.getString(3)
            val p2 = cursor.getString(4)
            val p3 = cursor.getString(5)
            val p4 = cursor.getString(6)
            val p5 = cursor.getString(7)
            val answer = cursor.getInt(8)
            wrongProblemList.add(AccountingData(pid, year, description, p1, p2, p3, p4, p5, answer)
            )
        }
        cursor.close()
        return wrongProblemList
    }

    fun remove(problem : AccountingData) {
        val db = this.writableDatabase
        if (db.delete(TABLE_NAME, "$PID = ${problem.pid} and $YEAR = ${problem.year}", null) == 0) {
            Log.e("DB remove", "Fail to remove $problem")
        }
    }

    fun clear() {
        val db = this.writableDatabase
        val dropTable = "delete from $TABLE_NAME"
        db?.execSQL(dropTable)
    }
}