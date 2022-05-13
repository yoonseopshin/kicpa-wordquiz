package com.cpa.cpa_word_problem.feature.quiz.presentation.mapper

import com.ysshin.cpaquiz.domain.model.Problem
import com.cpa.cpa_word_problem.feature.quiz.presentation.model.ProblemModel

fun Problem.toModel() = ProblemModel(
    year = year,
    pid = pid,
    description = description,
    subDescriptions = subDescriptions,
    questions = questions,
    answer = answer,
    type = type,
    source = source
)

fun ProblemModel.toDomain() = Problem(
    year = year,
    pid = pid,
    description = description,
    subDescriptions = subDescriptions,
    questions = questions,
    answer = answer,
    type = type,
    source = source
)

@JvmName("problemModelListToDomain")
fun List<ProblemModel>.toDomain() = map { it.toDomain() }

@JvmName("problemListToModel")
fun List<Problem>.toModel() = map { it.toModel() }

