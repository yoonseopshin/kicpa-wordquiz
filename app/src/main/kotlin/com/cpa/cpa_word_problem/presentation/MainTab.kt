package com.cpa.cpa_word_problem.presentation

import com.cpa.cpa_word_problem.R

enum class MainTab {
    Home, Note, Settings;

    companion object {
        fun getIcon(position: Int) =
            when (position) {
                Home.ordinal -> R.drawable.asld_home
                Note.ordinal -> R.drawable.asld_note
                Settings.ordinal -> R.drawable.asld_settings
                else -> throw IndexOutOfBoundsException()
            }
    }
}
