package com.cpa.cpa_word_problem.db

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(private val context: Context) {

    companion object {
        const val PREFERENCE_NAME = "settings_pref"
        const val PROBLEM_SIZE = "problem_size"
        const val DEFAULT_PROBLEM_SIZE = 3
        const val SELECTED_YEAR = "selected_year"
        const val DEFAULT_SELECTED_YEAR = 0b11111
    }

    private fun getPreferences(): SharedPreferences {
        return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    fun setProblemSize(value: Int) {
        val pref = getPreferences()
        val editor = pref.edit()
        editor.putInt(PROBLEM_SIZE, value)
        editor.apply()
    }

    fun getProblem(): Int {
        val pref = getPreferences()
        return pref.getInt(PROBLEM_SIZE, DEFAULT_PROBLEM_SIZE)
    }

    fun setSelectedYear(bitSet: Int) {
        val pref = getPreferences()
        val editor = pref.edit()
        editor.putInt(SELECTED_YEAR, bitSet)
        editor.apply()
    }

    fun getSelectedYear(): Int {
        val pref = getPreferences()
        return pref.getInt(SELECTED_YEAR, DEFAULT_SELECTED_YEAR)
    }

}