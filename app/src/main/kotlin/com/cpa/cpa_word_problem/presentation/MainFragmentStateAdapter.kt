package com.cpa.cpa_word_problem.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ysshin.cpaquiz.feature.home.presentation.screen.main.HomeComposeFragment
import com.ysshin.cpaquiz.feature.quiz.presentation.screen.main.NoteComposeFragment
import com.ysshin.cpaquiz.feature.settings.presentation.screen.main.SettingsFragment
import com.ysshin.cpaquiz.shared.android.util.newInstance

class MainFragmentStateAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val tabFragmentsCreators = mapOf(
        MainTab.Home.ordinal to { newInstance<HomeComposeFragment>() },
        MainTab.Note.ordinal to { newInstance<NoteComposeFragment>() },
        MainTab.Settings.ordinal to { newInstance<SettingsFragment>() },
    )

    override fun getItemCount() = tabFragmentsCreators.size

    override fun createFragment(position: Int): Fragment =
        tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
}
