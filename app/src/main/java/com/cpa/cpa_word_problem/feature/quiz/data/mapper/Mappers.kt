package com.cpa.cpa_word_problem.feature.quiz.data.mapper

import com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.ProblemEntity
import com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.WrongProblemEntity
import com.cpa.cpa_word_problem.feature.quiz.data.datasource.remote.ProblemResponse
import com.cpa.cpa_word_problem.feature.quiz.data.datasource.remote.ScheduledDateResponse
import com.ysshin.cpaquiz.domain.model.Problem
import com.ysshin.cpaquiz.domain.model.ScheduledDate
import com.ysshin.cpaquiz.domain.model.WrongProblem

fun ProblemEntity.toDomain() = Problem(
    year = year,
    pid = pid,
    description = description,
    subDescriptions = subDescriptions,
    questions = questions,
    answer = answer,
    type = type,
    source = source
)

@JvmName("problemEntityListToDomain")
fun List<ProblemEntity>.toDomain() = map { it.toDomain() }

fun Problem.toLocalData() = ProblemEntity(
    year = year,
    pid = pid,
    description = description,
    subDescriptions = subDescriptions,
    questions = questions,
    answer = answer,
    type = type,
    source = source
)

@JvmName("problemListToLocalData")
fun List<Problem>.toLocalData() = map { it.toLocalData() }

fun ProblemResponse.toDomain() = Problem(
    year = year,
    pid = pid,
    description = description,
    subDescriptions = subDescriptions,
    questions = questions,
    answer = answer,
    type = type,
    source = source
)

@JvmName("problemResponseListToDomain")
fun List<ProblemResponse>.toDomain() = map { it.toDomain() }

fun ScheduledDateResponse.toDomain() = ScheduledDate(date = date)

@JvmName("scheduledDateResponseListToDomain")
fun List<ScheduledDateResponse>.toDomain() = map { it.toDomain() }

fun WrongProblem.toLocalData() = WrongProblemEntity(
    pid = pid,
    year = year,
    type = type,
    createdAt = createdAt
)

@JvmName("wrongProblemListToLocalData")
fun List<WrongProblem>.toLocalData() = map { it.toLocalData() }

