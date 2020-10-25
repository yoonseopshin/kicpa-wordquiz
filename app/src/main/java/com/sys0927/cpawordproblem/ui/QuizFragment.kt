package com.sys0927.cpawordproblem.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sys0927.cpawordproblem.R
import com.sys0927.cpawordproblem.data.QuizState

class QuizFragment : Fragment() {

    private lateinit var childFragments: HashMap<QuizState, Fragment>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragments
        return inflater.inflate(R.layout.fragment_quiz, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        childFragments = hashMapOf(
            QuizState.Ready to QuizReadyFragment(),
            QuizState.Start to QuizStartFragment()
        )
        setFragment(QuizState.Ready)
    }

    fun setFragment(quizState: QuizState) {
        val fm = childFragmentManager
        val ta = fm.beginTransaction()
        ta.replace(R.id.quizFragmentContainer, childFragments[quizState]!!)
        ta.commit()
    }

}