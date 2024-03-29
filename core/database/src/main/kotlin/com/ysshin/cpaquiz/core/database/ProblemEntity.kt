package com.ysshin.cpaquiz.core.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.ysshin.cpaquiz.core.database.AppContract.Problem
import com.ysshin.cpaquiz.domain.model.ProblemSource
import com.ysshin.cpaquiz.domain.model.QuizType
import java.util.Objects

@Entity(
    tableName = Problem.TABLE_NAME,
    primaryKeys = [Problem.PID, Problem.YEAR, Problem.TYPE, Problem.SOURCE],
)
data class ProblemEntity(
    @ColumnInfo(name = Problem.PID) val pid: Int,
    @ColumnInfo(name = Problem.YEAR) val year: Int,
    @ColumnInfo(name = Problem.DESCRIPTION) val description: String,
    @ColumnInfo(name = Problem.SUB_DESCRIPTION) val subDescriptions: List<String> = emptyList(),
    @ColumnInfo(name = Problem.QUESTIONS) val questions: List<String>,
    @ColumnInfo(name = Problem.ANSWER) val answer: Int,
    @ColumnInfo(name = Problem.TYPE) val type: QuizType,
    @ColumnInfo(name = Problem.SOURCE) val source: ProblemSource,
    @ColumnInfo(name = Problem.SUBTYPE) val subtype: String,
) {
    override fun equals(other: Any?): Boolean {
        (other as? ProblemEntity)?.let {
            return year == it.year && pid == it.pid && type == it.type && source == it.source
        } ?: return super.equals(other)
    }

    override fun hashCode() = Objects.hash(pid, year, type, source)
}
