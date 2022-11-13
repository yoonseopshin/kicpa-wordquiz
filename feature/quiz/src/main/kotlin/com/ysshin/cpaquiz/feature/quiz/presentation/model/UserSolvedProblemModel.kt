package com.ysshin.cpaquiz.feature.quiz.presentation.model

import com.ysshin.cpaquiz.core.common.DEFAULT_INT
import com.ysshin.cpaquiz.core.common.DEFAULT_INVALID_INT
import com.ysshin.cpaquiz.domain.model.Problem

data class UserSolvedProblemModel(
    val elapsedTime: Int = DEFAULT_INT,
    val userSelectedIndex: Int = DEFAULT_INVALID_INT,
    val problem: Problem,
    val createdAt: Long = System.currentTimeMillis()
)

@JvmName("userSolvedProblemModelJoin")
fun List<UserSolvedProblemModel>.from(
    elapsedTimes: List<Int>,
    userSelectedIndices: List<Int>,
    problems: List<Problem>
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
                    problem = problems[i]
                )
            )
        }
    }.toList()
}
