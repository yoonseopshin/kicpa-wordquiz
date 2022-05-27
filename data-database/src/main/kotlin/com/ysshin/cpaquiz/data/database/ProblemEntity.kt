package com.ysshin.cpaquiz.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.ysshin.cpaquiz.data.database.AppContract.Problem
import com.ysshin.cpaquiz.domain.model.ProblemSource
import com.ysshin.cpaquiz.domain.model.QuizType
import java.util.*

@Entity(
        tableName = Problem.TABLE_NAME,
        primaryKeys = [Problem.PID, Problem.YEAR, Problem.TYPE]
)
data class ProblemEntity(
        @ColumnInfo(name = Problem.PID) val pid: Int = 0,
        @ColumnInfo(name = Problem.YEAR) val year: Int = 0,
        @ColumnInfo(name = Problem.DESCRIPTION) val description: String = "",
        @ColumnInfo(name = Problem.SUB_DESCRIPTION) val subDescriptions: List<String> = emptyList(),
        @ColumnInfo(name = Problem.QUESTIONS) val questions: List<String> = emptyList(),
        @ColumnInfo(name = Problem.ANSWER) val answer: Int = 0,
        @ColumnInfo(name = Problem.TYPE) val type: QuizType = QuizType.None,
        @ColumnInfo(name = Problem.SOURCE) val source: ProblemSource = ProblemSource.None
) {
    override fun equals(other: Any?): Boolean {
        (other as? ProblemEntity)?.let {
            return year == it.year && pid == it.pid && type == it.type
        } ?: return super.equals(other)
    }

    override fun hashCode() = Objects.hash(pid, year, type)
}