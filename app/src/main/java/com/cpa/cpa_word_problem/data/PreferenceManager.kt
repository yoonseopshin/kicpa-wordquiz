package com.cpa.cpa_word_problem.data

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(private val context: Context) {

    companion object {
        const val PREFERENCE_NAME = "settings_pref"
        const val PROBLEM_SIZE = "problem_size"
        const val DEFAULT_PROBLEM_SIZE = 3
        const val SELECTED_YEAR = "selected_year"
        const val DEFAULT_SELECTED_YEAR = 0b11111
        const val QUIZ_EFFECT = "quiz_effect"
        const val DEFAULT_QUIZ_EFFECT = true
        const val CATEGORY = "category"
        const val DEFAULT_CATEGORY = "회계학"
    }

    private fun getPreferences(): SharedPreferences {
        return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    fun setSelectedProblemSize(value: Int) {
        val pref = getPreferences()
        val editor = pref.edit()
        editor.putInt(PROBLEM_SIZE, value)
        editor.apply()
    }

    fun getSelectedProblemSize(): Int {
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

    fun setQuizEffect(isTurnOn: Boolean) {
        val pref = getPreferences()
        val editor = pref.edit()
        editor.putBoolean(QUIZ_EFFECT, isTurnOn)
        editor.apply()
    }

    fun getQuizEffect(): Boolean {
        val pref = getPreferences()
        return pref.getBoolean(QUIZ_EFFECT, DEFAULT_QUIZ_EFFECT)
    }

    fun setCategory(category: String) {
        val pref = getPreferences()
        val editor = pref.edit()
        editor.putString(CATEGORY, category)
        editor.apply()
    }

    fun getCategory(): String {
        val pref = getPreferences()
        return pref.getString(CATEGORY, DEFAULT_CATEGORY)!!
    }

}