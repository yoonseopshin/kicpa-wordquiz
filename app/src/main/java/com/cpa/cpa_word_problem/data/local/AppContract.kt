package com.cpa.cpa_word_problem.data.model.local

object AppContract {

    const val DATABASE_NAME = "cpaquiz_db"

    object ProblemEntity {
        const val TABLE_NAME = "problems"
        const val PID = "pid"
        const val YEAR = "year"
        const val DESCRIPTION = "description"
        const val SUB_DESCRIPTION = "sub_description"
        const val QUESTIONS = "questions"
        const val ANSWER = "answer"
        const val TYPE = "type"
    }
}