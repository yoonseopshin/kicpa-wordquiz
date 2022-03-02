package com.cpa.cpa_word_problem.feature.quiz.data.datasource.local

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
    }

    object WrongProblem {
        const val TABLE_NAME = "wrong_problems"
        const val CREATED_AT = "created_at"
    }
}