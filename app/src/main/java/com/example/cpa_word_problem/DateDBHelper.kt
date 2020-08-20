package com.example.cpa_word_problem

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.time.LocalDate

class DateDBHelper(context: Context): SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "date.db"
        const val TABLE_NAME = "dates"
        const val DATE = "date"
        const val SOLVED = "solved"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "create table if not exists $TABLE_NAME (" +
                "$DATE text primary key, " +
                "$SOLVED integer)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTable = "drop table if exists $TABLE_NAME"
        db?.execSQL(dropTable)
        onCreate(db)
    }

    fun insertToday() {
        val db = this.writableDatabase
        val contentValue = ContentValues()
        contentValue.put(DATE, LocalDate.now().toString())
        contentValue.put(SOLVED, 0)
        db.insert(TABLE_NAME, null, contentValue)
    }

    fun fetch(): ArrayList<DateTaskData> {
        val db = this.readableDatabase
        val fetchAll = "select * from $TABLE_NAME"
        val cursor = db.rawQuery(fetchAll, null)
        val dates = arrayListOf<DateTaskData>()
        while (cursor.moveToNext()) {
            val date = cursor.getString(0)
            val solved = cursor.getInt(1)
            dates.add(DateTaskData(date, solved))
        }
        cursor.close()
        return dates
    }
}