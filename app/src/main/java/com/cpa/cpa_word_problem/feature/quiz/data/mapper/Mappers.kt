package com.cpa.cpa_word_problem.feature.quiz.data.mapper

import com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.ProblemEntity
import com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.WrongProblemEntity
import com.cpa.cpa_word_problem.feature.quiz.data.datasource.remote.ProblemResponse
import com.cpa.cpa_word_problem.feature.quiz.data.datasource.remote.ScheduledDateResponse
import com.cpa.cpa_word_problem.feature.quiz.domain.model.Problem
import com.cpa.cpa_word_problem.feature.quiz.domain.model.ScheduledDate
import com.cpa.cpa_word_problem.feature.quiz.domain.model.WrongProblem

fun ProblemEntity.toDomain() = Problem(
    year = year,
    pid = pid,
    description = description,
    subDescriptions = subDescriptions,
    questions = questions,
    answer = answer,
    type = type,
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

