package com.example.cpa_word_problem

import java.util.*

data class AccountingData(val pid: Int,
                          val year: Int,
                          val description: String,
                          val p1: String,
                          val p2: String,
                          val p3: String,
                          val p4: String,
                          val p5: String,
                          val answer: Int) {

    override fun equals(other: Any?): Boolean {
        (other as? AccountingData)?.let {
            return this.pid == it.pid && this.year == it.year
        } ?: return super.equals(other)
    }

    override fun hashCode(): Int {
        return Objects.hash(this.pid, this.year)
    }
}