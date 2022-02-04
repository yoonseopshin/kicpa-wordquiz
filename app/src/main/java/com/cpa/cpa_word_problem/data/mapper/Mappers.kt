package com.cpa.cpa_word_problem.data.mapper

import com.cpa.cpa_word_problem.data.model.local.Problem

fun Problem.toDomain() = com.cpa.cpa_word_problem.domain.model.Problem(
    year = year,
    pid = pid,
    description = description,
    subDescriptions = subDescriptions,
    questions = questions,
    answer = answer,
    type = type,
)