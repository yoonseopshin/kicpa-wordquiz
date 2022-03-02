package com.cpa.cpa_word_problem.feature.quiz.presentation.screen.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cpa.cpa_word_problem.feature.quiz.presentation.screen.main.home.HomeFragment
import com.cpa.cpa_word_problem.feature.quiz.presentation.screen.main.note.NoteFragment
import com.cpa.cpa_word_problem.feature.quiz.presentation.screen.main.settings.SettingsFragment

const val HOME_PAGE_INDEX = 0
const val NOTE_PAGE_INDEX = 1
const val SETTINGS_PAGE_INDEX = 2

class MainFragmentStateAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val tabFragmentsCreators = mapOf(
        HOME_PAGE_INDEX to { HomeFragment() },
        NOTE_PAGE_INDEX to { NoteFragment() },
        SETTINGS_PAGE_INDEX to { SettingsFragment() }
    )

    override fun getItemCount() = tabFragmentsCreators.size

    override fun createFragment(position: Int): Fragment =
        tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
}