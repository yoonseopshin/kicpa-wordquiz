package com.ysshin.cpaquiz.feature.quiz.presentation.model

import com.ysshin.cpaquiz.core.base.DEFAULT_INVALID_INT
import com.ysshin.cpaquiz.core.base.DEFAULT_LONG
import com.ysshin.cpaquiz.domain.model.Problem

data class UserSolvedProblemModel(
    val elapsedTime: Long = DEFAULT_LONG,
    val userSelectedIndex: Int = DEFAULT_INVALID_INT,
    val createdAt: Long = System.currentTimeMillis(),
    val problem: Problem,
)

@JvmName("userSolvedProblemModelJoin")
fun List<UserSolvedProblemModel>.from(
    elapsedTimes: List<Long>,
    userSelectedIndices: List<Int>,
    problems: List<Problem>,
): List<UserSolvedProblemModel> {
    val listSize = elapsedTimes.size
    assert(listSize == userSelectedIndices.size && listSize == problems.size) {
        "Invalid size: elapsed-${elapsedTimes.size} selected-${userSelectedIndices.size} problems-${problems.size}"
    }

    return mutableListOf<UserSolvedProblemModel>().apply {
        for (i in 0 until listSize) {
            add(
                UserSolvedProblemModel(
                    elapsedTime = elapsedTimes[i],
                    userSelectedIndex = userSelectedIndices[i],
                    problem = problems[i],
                ),
            )
        }
    }.toList()
}
