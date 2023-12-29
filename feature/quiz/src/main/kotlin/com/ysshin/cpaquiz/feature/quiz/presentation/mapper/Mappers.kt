package com.ysshin.cpaquiz.feature.quiz.presentation.mapper

import com.ysshin.cpaquiz.domain.model.Problem
import com.ysshin.cpaquiz.feature.quiz.presentation.model.ProblemModel
import com.ysshin.cpaquiz.feature.quiz.presentation.model.WrongProblemModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

fun Problem.toModel() = ProblemModel(
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

fun ProblemModel.toDomain() = Problem(
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

@JvmName("problemModelListToDomain")
fun List<ProblemModel>.toDomain(): ImmutableList<Problem> = map { it.toDomain() }.toImmutableList()

@JvmName("problemListToModel")
fun List<Problem>.toModel() = map { it.toModel() }

fun Problem.toWrongProblemModel() = WrongProblemModel(this)
