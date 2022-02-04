package com.cpa.cpaquiz.data.mapper

import com.cpa.cpaquiz.data.model.Problem

fun Problem.toDomain() = com.cpa.cpaquiz.domain.model.Problem(
    year = year,
    pid = pid,
    description = description,
    subDescriptions = subDescriptions,
    questions = questions,
    answer = answer,
    type = type,
)