package com.cpa.cpa_word_problem.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.cpa.cpa_word_problem.R
import com.cpa.cpa_word_problem.data.QuizOption
import com.cpa.cpa_word_problem.data.QuizState
import com.kakao.adfit.ads.AdListener
import kotlinx.android.synthetic.main.fragment_quiz_ready.*

class QuizReadyFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz_ready, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setSelectedYear()
        setQuizButtonListener()
        setProblemSize()
        setCategory()
        setCenterAdView()
    }

    private fun setQuizButtonListener() {
        val activity = requireActivity() as MainActivity
        val viewModel = activity.viewModel

        startBtn.setOnClickListener {
            val years = arrayListOf<Int>()
            for (year in viewModel.startYear..viewModel.endYear) {
                val checkBox = checkBoxFlowLayout.findViewWithTag<CheckBox>(year) ?: continue
                if (checkBox.isChecked) years.add(year)
            }

            if (years.isEmpty()) {
                Toast.makeText(activity, getString(R.string.select_problems), Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            val problemString = problemSpinner.selectedItem.toString()
            viewModel.probSize = problemString.replace("[^0-9]".toRegex(), "").toInt()
            val category = categorySpinner.selectedItem.toString()
            viewModel.quizOption = QuizOption(years, category)

            setFragment(QuizState.Start)
        }
    }

    private fun setFragment(quizState: QuizState) {
        (requireParentFragment() as QuizFragment).setFragment(quizState)
    }

    private fun setProblemSize() {
        problemSpinner.adapter = ArrayAdapter(
            requireContext(), R.layout.spinner_item,
            resources.getStringArray(R.array.problems)
        )
    }

    private fun setCategory() {
        categorySpinner.adapter = ArrayAdapter(
            requireContext(), R.layout.spinner_item,
            resources.getStringArray(R.array.categories)
        )
    }

    private fun setSelectedYear() {
        val activity = requireActivity() as MainActivity
        val viewModel = activity.viewModel
        viewModel.checkBoxArray =
            Array(viewModel.endYear - viewModel.startYear + 1) { CheckBox(activity) }

        for (year in viewModel.startYear..viewModel.endYear) {
            val yearIdx = year - viewModel.startYear
            val checkBox = viewModel.checkBoxArray[yearIdx]
            checkBox.text = year.toString()
            checkBox.isChecked = true
            checkBox.tag = year
            checkBox.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
            checkBoxFlowLayout.addView(checkBox)
        }
    }

    private fun updateSelectedYear() {
        val activity = requireActivity() as MainActivity
        val viewModel = activity.viewModel
        val checkBoxBitSet = viewModel.getSelectedYear()
        val yearSize = viewModel.endYear - viewModel.startYear + 1
        for (i in 0 until yearSize) {
            val bit = 1 shl i
            val result = checkBoxBitSet and bit
            viewModel.checkBoxArray[i].isChecked = result == bit
        }
    }

    private fun updateProblemSize() {
        val activity = requireActivity() as MainActivity
        val viewModel = activity.viewModel
        val position = viewModel.problemToPosition[viewModel.getSelectedProblemSize()]
        position?.let { problemSpinner.setSelection(it) }
    }

    private fun updateCategory() {
        val activity = requireActivity() as MainActivity
        val viewModel = activity.viewModel
        val position = viewModel.categoryToPosition[viewModel.getCategory()]
        position?.let { categorySpinner.setSelection(it) }
    }

    override fun onResume() {
        super.onResume()
        updateProblemSize()
        updateSelectedYear()
        updateCategory()
    }

    private fun setCenterAdView() {
        centerAdView.setClientId("DAN-vf9782z0rlmg")
        centerAdView.setAdListener(object : AdListener {
            override fun onAdLoaded() {
                Log.d("banner", "Ad banner loaded")
            }

            override fun onAdFailed(errorCode: Int) {
                Log.e("banner", "$errorCode")
            }

            override fun onAdClicked() {
                Log.d("banner", "Ad banner clicked")
            }
        })

        lifecycle.addObserver(object: LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
            fun onResume() {
                centerAdView?.resume()
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
            fun onPause() {
                centerAdView?.pause()
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroy() {
                centerAdView?.destroy()
            }
        })
        centerAdView.loadAd()
    }
}