package com.ysshin.cpaquiz.data.mapper

import com.ysshin.cpaquiz.data.database.ProblemEntity
import com.ysshin.cpaquiz.data.database.WrongProblemEntity
import com.ysshin.cpaquiz.data.network.response.ProblemResponse
import com.ysshin.cpaquiz.data.network.response.ScheduledDateResponse
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
    source = source,
    subtype = subtype,
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
    source = source,
    subtype = subtype,
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
    source = source,
    subtype = subtype,
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
    source = source,
    createdAt = createdAt
)

@JvmName("wrongProblemListToLocalData")
fun List<WrongProblem>.toLocalData() = map { it.toLocalData() }
