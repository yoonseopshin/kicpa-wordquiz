package com.ysshin.shared.util

object TimeFormatter {

    fun format(millis: Long): String {
        val minutes = millis / 1000 / 60
        val seconds = millis / 1000 % 60

        return if (minutes == 0L) {
            "$seconds"
        } else {
            if (seconds >= 10) {
                "${minutes}:${seconds}"
            } else {
                "${minutes}:0${seconds}"
            }
        }
    }

    fun format(millis: Int): String = format(millis.toLong())

    fun formatKorean(millis: Long): String {
        val minutes = millis / 1000 / 60
        val seconds = millis / 1000 % 60
        val round = if (millis % 1000 >= 500) 1 else 0

        return if (minutes == 0L) {
            "${seconds + round}초"
        } else {
            "${minutes}분 ${seconds + round}초"
        }
    }

    fun formatKorean(millis: Int): String = formatKorean(millis.toLong())

}