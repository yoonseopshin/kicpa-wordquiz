package com.ysshin.cpaquiz.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.ysshin.cpaquiz.data.database.AppContract.Problem
import com.ysshin.cpaquiz.data.database.AppContract.WrongProblem
import com.ysshin.cpaquiz.domain.model.QuizType
import java.util.*

@Entity(
    tableName = WrongProblem.TABLE_NAME,
    primaryKeys = [Problem.PID, Problem.YEAR, Problem.TYPE]
)
data class WrongProblemEntity(
    @ColumnInfo(name = Problem.PID) val pid: Int,
    @ColumnInfo(name = Problem.YEAR) val year: Int,
    @ColumnInfo(name = Problem.TYPE) val type: QuizType,
    @ColumnInfo(name = WrongProblem.CREATED_AT) val createdAt: Long = System.currentTimeMillis()
) {
    override fun equals(other: Any?): Boolean {
        (other as? WrongProblemEntity)?.let {
            return year == it.year && pid == it.pid && type == it.type && createdAt == it.createdAt
        } ?: return super.equals(other)
    }

    override fun hashCode() = Objects.hash(pid, year, type, createdAt)
}
