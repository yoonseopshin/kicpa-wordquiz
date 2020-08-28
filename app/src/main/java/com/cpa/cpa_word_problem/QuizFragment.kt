package com.cpa.cpa_word_problem

import android.animation.ObjectAnimator
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.cpa.cpa_word_problem.db.ProblemData
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_quiz.*

class QuizFragment : Fragment() {

    private lateinit var selectedProblem: ProblemData
    private lateinit var quizOption: QuizOption
    private lateinit var wrongProblems: ArrayList<ProblemData>
    private val problemToPosition = hashMapOf<Int, Int>()
    private val checkBoxList = arrayListOf<CheckBox>()

    init {
        problemToPosition[3] = 0
        problemToPosition[5] = 1
        problemToPosition[7] = 2
        problemToPosition[10] = 3
    }

    companion object {
        const val DURATION = 500L
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragments
        return inflater.inflate(R.layout.fragment_quiz, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    private fun init() {
        val activity = requireActivity() as MainActivity
        val viewModel = activity.viewModel
        setInfoVisibility(View.VISIBLE)
        setProblemVisibility(View.GONE)

        quizBtn.setOnClickListener {
            wrongProblems = arrayListOf()

            val years = arrayListOf<Int>()
            for (year in viewModel.startYear..viewModel.endYear) {
                val checkBox = checkBoxLayout.findViewWithTag<CheckBox>(year)
                if (checkBox.isChecked) {
                    years.add(year)
                }
            }

            if (years.isEmpty()) {
                Toast.makeText(activity, "출제년도를 선택하세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val problemString = problemSpinner.selectedItem.toString()
            viewModel.probSize = problemString.replace("[^0-9]".toRegex(), "").toInt()
            quizOption = QuizOption(years)
            viewModel.turn = 1

            setInfoVisibility(View.GONE)
            setProblemVisibility(View.VISIBLE)

            setQuiz(quizOption)
            activity.viewPager2.isUserInputEnabled = false
        }

        submitBtn.setOnClickListener {
            val radioBtn =
                radioGroup.findViewById<View>(radioGroup.checkedRadioButtonId) as RadioButton
            val pno = radioGroup.indexOfChild(radioBtn) + 1

            val backgroundColor =
                (quizWholeLayout.background as? ColorDrawable)?.color ?: Color.TRANSPARENT

            if (isCorrect(pno)) {
                val animColor = ContextCompat.getColor(activity, R.color.success)
                showAnswerAnimation(backgroundColor, animColor)
            } else {
                val animColor = ContextCompat.getColor(activity, R.color.wrong)
                showAnswerAnimation(backgroundColor, animColor)
                wrongProblems.add(selectedProblem)
            }

            if (viewModel.turn++ >= viewModel.probSize) {
                Handler().postDelayed({
                    setProblemVisibility(View.GONE)
                    setInfoVisibility(View.VISIBLE)
                    activity.viewPager2.isUserInputEnabled = true
                    viewModel.addWrongProblems(wrongProblems)
                }, DURATION)
            } else {
                setQuiz(quizOption)
            }
        }

        problemSpinner.adapter = ArrayAdapter(
            requireContext(), R.layout.spinner_item,
            resources.getStringArray(R.array.problems)
        )

        for (year in viewModel.startYear..viewModel.endYear) {
            val checkBox = CheckBox(activity)
            checkBox.text = year.toString()
            checkBox.isChecked = true
            checkBox.tag = year
            checkBoxList.add(checkBox)
            checkBoxLayout.addView(checkBox)
        }
    }

    override fun onPause() {
        super.onPause()
        setProblemVisibility(View.GONE)
    }

    override fun onResume() {
        super.onResume()
        setInfoVisibility(View.VISIBLE)
        updateProblemSize()
        updateSelectedYear()
    }

    private fun showAnswerAnimation(backgroundColor: Int, animColor: Int) {
        ObjectAnimator.ofArgb(
            quizWholeLayout,
            "backgroundColor",
            animColor,
            backgroundColor
        ).apply {
            duration = DURATION
            start()
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
            checkBoxList[i].isChecked = result == bit
        }
    }

    private fun updateProblemSize() {
        val activity = requireActivity() as MainActivity
        val viewModel = activity.viewModel
        val position = problemToPosition[viewModel.getSelectedProblemSize()]
        position?.let { problemSpinner.setSelection(it) }
    }

    private fun setQuiz(option: QuizOption) {
        val activity = requireActivity() as MainActivity
        val viewModel = activity.viewModel
        radioButton.isChecked = true

        selectedProblem = viewModel.getRandomProblem(option)

        descriptionTextView.text = selectedProblem.description
        infoTextView.text = getInfoText(selectedProblem)
        radioButton.text = selectedProblem.p1
        radioButton2.text = selectedProblem.p2
        radioButton3.text = selectedProblem.p3
        radioButton4.text = selectedProblem.p4
        radioButton5.text = selectedProblem.p5

        setProblemVisibility(View.VISIBLE)
        setInfoVisibility(View.GONE)
    }

    private fun setInfoVisibility(visibility: Int) {
        cardView.visibility = visibility
        quizBtn.visibility = visibility
        quizDescriptionTextView.visibility = visibility
    }

    private fun setProblemVisibility(visibility: Int) {
        descriptionTextView.visibility = visibility
        infoTextView.visibility = visibility
        scrollView.visibility = visibility
        submitBtn.visibility = visibility
    }

    private fun getInfoText(problem: ProblemData) =
        StringBuilder("${problem.year}년 ${problem.pid}번").toString()

    private fun isCorrect(pno: Int) = selectedProblem.answer == pno

}