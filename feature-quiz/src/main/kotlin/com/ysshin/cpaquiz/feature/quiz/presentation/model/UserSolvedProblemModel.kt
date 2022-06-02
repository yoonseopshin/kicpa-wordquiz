package com.ysshin.cpaquiz.feature.quiz.presentation.model

import com.ysshin.cpaquiz.domain.model.Problem
import com.ysshin.cpaquiz.shared.base.DEFAULT_INT
import com.ysshin.cpaquiz.shared.base.DEFAULT_INVALID_INT

data class UserSolvedProblemModel(
    val elapsedTime: Int = DEFAULT_INT,
    val userSelectedIndex: Int = DEFAULT_INVALID_INT,
    val problem: Problem = Problem(),
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
        "Invalid size among elapsedTimes, userSelectedIndices and problems"
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
