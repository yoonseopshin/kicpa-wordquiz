package com.ysshin.cpaquiz.data.database

object AppContract {

    const val DATABASE_NAME = "cpaquiz_db"

    object Problem {
        const val TABLE_NAME = "problems"
        const val PID = "pid"
        const val YEAR = "year"
        const val DESCRIPTION = "description"
        const val SUB_DESCRIPTION = "sub_description"
        const val QUESTIONS = "questions"
        const val ANSWER = "answer"
        const val TYPE = "type"
        const val SOURCE = "source"
        const val SUBTYPE = "subtype"
    }

    object WrongProblem {
        const val TABLE_NAME = "wrong_problems"
        const val CREATED_AT = "created_at"
    }
}
