package com.cpa.cpa_word_problem.feature.quiz.domain.model

import com.cpa.cpa_word_problem.utils.DEFAULT_INT
import com.cpa.cpa_word_problem.utils.DEFAULT_STRING
import com.cpa.cpa_word_problem.utils.DEFAULT_STRING_LIST

data class Problem(
    val year: Int = DEFAULT_INT,
    val pid: Int = DEFAULT_INT,
    val description: String = DEFAULT_STRING,
    val subDescriptions: List<String> = DEFAULT_STRING_LIST,
    val questions: List<String> = DEFAULT_STRING_LIST,
    val answer: Int = DEFAULT_INT,
    val type: QuizType = QuizType.None,
)

fun Problem.isValid(): Boolean =
    year != DEFAULT_INT
            || pid != DEFAULT_INT
            || description != DEFAULT_STRING
            || subDescriptions != DEFAULT_STRING_LIST
            || questions != DEFAULT_STRING_LIST
            || answer != DEFAULT_INT
            || type != QuizType.None