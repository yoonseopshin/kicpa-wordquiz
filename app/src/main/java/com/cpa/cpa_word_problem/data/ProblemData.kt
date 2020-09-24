package com.cpa.cpa_word_problem.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import java.util.*

@Entity(tableName = "problems", primaryKeys = ["pid", "year"])
data class ProblemData(
    @ColumnInfo(name = ProblemContract.ProblemEntity.PID) val pid: Int,
    @ColumnInfo(name = ProblemContract.ProblemEntity.YEAR) val year: Int,
    @ColumnInfo(name = ProblemContract.ProblemEntity.DESCRIPTION) val description: String,
    @ColumnInfo(name = ProblemContract.ProblemEntity.P1) val p1: String,
    @ColumnInfo(name = ProblemContract.ProblemEntity.P2) val p2: String,
    @ColumnInfo(name = ProblemContract.ProblemEntity.P3) val p3: String,
    @ColumnInfo(name = ProblemContract.ProblemEntity.P4) val p4: String,
    @ColumnInfo(name = ProblemContract.ProblemEntity.P5) val p5: String,
    @ColumnInfo(name = ProblemContract.ProblemEntity.ANSWER) val answer: Int,
    @ColumnInfo(name = ProblemContract.ProblemEntity.TYPE) val type: String
) {

    override fun equals(other: Any?): Boolean {
        (other as? ProblemData)?.let {
            return this.pid == it.pid && this.year == it.year
        } ?: return super.equals(other)
    }

    override fun hashCode(): Int {
        return Objects.hash(this.pid, this.year)
    }
}