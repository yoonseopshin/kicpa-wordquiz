package com.ysshin.cpaquiz.core.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.ysshin.cpaquiz.core.database.AppContract.Problem
import com.ysshin.cpaquiz.core.database.AppContract.WrongProblem
import com.ysshin.cpaquiz.domain.model.ProblemSource
import com.ysshin.cpaquiz.domain.model.QuizType
import java.util.Objects

@Entity(
    tableName = WrongProblem.TABLE_NAME,
    primaryKeys = [Problem.PID, Problem.YEAR, Problem.TYPE, Problem.SOURCE],
)
data class WrongProblemEntity(
    @ColumnInfo(name = Problem.PID) val pid: Int,
    @ColumnInfo(name = Problem.YEAR) val year: Int,
    @ColumnInfo(name = Problem.TYPE) val type: QuizType,
    @ColumnInfo(name = Problem.SOURCE) val source: ProblemSource,
    @ColumnInfo(name = WrongProblem.CREATED_AT) val createdAt: Long = System.currentTimeMillis(),
) {
    override fun equals(other: Any?): Boolean {
        (other as? WrongProblemEntity)?.let {
            return year == it.year &&
                pid == it.pid &&
                type == it.type &&
                source == it.source &&
                createdAt == it.createdAt
        } ?: return super.equals(other)
    }

    override fun hashCode() = Objects.hash(pid, year, type, source, createdAt)
}
