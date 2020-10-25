package com.cpa.cpa_word_problem.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cpa.cpa_word_problem.ui.NoteFragment
import com.cpa.cpa_word_problem.ui.QuizFragment
import com.cpa.cpa_word_problem.ui.SettingFragment

class MyFragmentStateAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {

        return when (position) {
            1 -> NoteFragment()
            2 -> SettingFragment()
            else -> QuizFragment()
        }
    }

}