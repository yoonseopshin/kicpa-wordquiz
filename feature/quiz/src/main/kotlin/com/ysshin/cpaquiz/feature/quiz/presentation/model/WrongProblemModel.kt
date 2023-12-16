package com.ysshin.cpaquiz.feature.quiz.presentation.model

import com.ysshin.cpaquiz.domain.model.Problem
import java.util.Objects

data class WrongProblemModel(val problem: Problem) {
    private val isWrong = true

    override fun hashCode(): Int {
        return Objects.hash(problem.year, problem.pid, problem.type, isWrong)
    }

    override fun equals(other: Any?): Boolean {
        return if (other is WrongProblemModel) {
            problem == other.problem && isWrong == other.isWrong
        } else {
            false
        }
    }
}
