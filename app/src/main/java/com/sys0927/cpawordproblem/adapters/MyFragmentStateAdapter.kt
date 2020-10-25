package com.sys0927.cpawordproblem.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sys0927.cpawordproblem.ui.NoteFragment
import com.sys0927.cpawordproblem.ui.QuizFragment
import com.sys0927.cpawordproblem.ui.SettingFragment

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