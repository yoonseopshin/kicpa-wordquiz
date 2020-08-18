package com.example.cpa_word_problem

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(private val context: Context) {

    companion object {
        const val PREFERENCE_NAME = "settings_pref"
        const val PROBLEM_SIZE = "problem_size"
        const val DEFAULT_PROBLEM_SIZE = 3
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

}