package com.cpa.cpa_word_problem.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.cpa.cpa_word_problem.data.model.AppContract.ProblemEntity
import com.cpa.cpa_word_problem.domain.model.Type
import java.util.*

@Entity(
    tableName = ProblemEntity.TABLE_NAME,
    primaryKeys = [ProblemEntity.PID, ProblemEntity.YEAR])
data class Problem(
    @ColumnInfo(name = ProblemEntity.PID) val pid: Int = 0,
    @ColumnInfo(name = ProblemEntity.YEAR) val year: Int = 0,
    @ColumnInfo(name = ProblemEntity.DESCRIPTION) val description: String = "",
    @ColumnInfo(name = ProblemEntity.SUB_DESCRIPTION) val subDescriptions: List<String> = emptyList(),
    @ColumnInfo(name = ProblemEntity.QUESTIONS) val questions: List<String> = emptyList(),
    @ColumnInfo(name = ProblemEntity.ANSWER) val answer: Int = 0,
    @ColumnInfo(name = ProblemEntity.TYPE) val type: Type = Type.Accounting
) {
    override fun equals(other: Any?): Boolean {
        (other as? Problem)?.let {
            return this.pid == it.pid && this.year == it.year
        } ?: return super.equals(other)
    }

    override fun hashCode() = Objects.hash(this.pid, this.year)
}