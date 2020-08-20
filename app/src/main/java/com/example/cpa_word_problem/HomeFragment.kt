package com.example.cpa_word_problem

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class HomeFragment : Fragment() {

    lateinit var dates: List<DateTaskData>
    lateinit var activity: MainActivity
    lateinit var dateDBHelper: DateDBHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        activity = requireActivity() as MainActivity
        dateDBHelper = activity.dateDBHelper
        dateDBHelper.insertToday()
    }

    override fun onResume() {
        dates = dateDBHelper.fetch()
        val last = dates.last()
        Log.d("last", last.date)
        Log.d("last", last.solved.toString())
        super.onResume()
    }
}