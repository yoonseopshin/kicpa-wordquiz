package com.cpa.cpa_word_problem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.fragment.app.Fragment
import com.cpa.cpa_word_problem.db.ProblemData
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_quiz.*

class QuizFragment : Fragment() {

    private lateinit var activity: MainActivity
    private lateinit var selectedProblem: ProblemData
    private lateinit var quizOption: QuizOption
    private var turn = 1
    private var problemSize = 0
    private lateinit var wrongProblems: ArrayList<ProblemData>
    private val problemToPosition = hashMapOf<Int, Int>()
    private val checkBoxList = arrayListOf<CheckBox>()

    init {
        problemToPosition[3] = 0
        problemToPosition[5] = 1
        problemToPosition[7] = 2
        problemToPosition[10] = 3
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
        activity = requireActivity() as MainActivity
        setInfoVisibility(View.VISIBLE)
        setProblemVisibility(View.GONE)

        quizBtn.setOnClickListener {
            wrongProblems = arrayListOf()
            val years = arrayListOf<Int>()
            for (year in activity.startYear..activity.endYear) {
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
            problemSize = problemString.replace("[^0-9]".toRegex(), "").toInt()
            quizOption = QuizOption(years)
            turn = 1

            setInfoVisibility(View.GONE)
            setProblemVisibility(View.VISIBLE)

            setQuiz(quizOption)
            activity.viewPager2.isUserInputEnabled = false
        }

        submitBtn.setOnClickListener {
            val radioBtn =
                radioGroup.findViewById<View>(radioGroup.checkedRadioButtonId) as RadioButton
            val pno = radioGroup.indexOfChild(radioBtn) + 1

            if (isCorrect(pno)) {
                val imageView: ImageView = activity.findViewById(R.id.successImageView)
                val anim = AnimationUtils.loadAnimation(activity, R.anim.pop_image)
                imageView.startAnimation(anim)
            } else {
                val imageView: ImageView = activity.findViewById(R.id.failImageView)
                val anim = AnimationUtils.loadAnimation(activity, R.anim.pop_image)
                imageView.startAnimation(anim)
                wrongProblems.add(selectedProblem)
            }

            if (turn++ >= problemSize) {
                setProblemVisibility(View.GONE)
                setInfoVisibility(View.VISIBLE)
                activity.viewPager2.isUserInputEnabled = true

                activity.wrongProblems.addAll(wrongProblems)
            } else {
                setQuiz(quizOption)
            }
        }

        problemSpinner.adapter = ArrayAdapter(
            requireContext(), R.layout.spinner_item,
            resources.getStringArray(R.array.problems)
        )


        for (year in activity.startYear..activity.endYear) {
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

    private fun updateSelectedYear() {
        val checkBoxBitSet = activity.preferenceManager.getSelectedYear()
        val yearSize = activity.endYear - activity.startYear + 1
        for (i in 0 until yearSize) {
            val bit = 1 shl i
            val result = checkBoxBitSet and bit
            checkBoxList[i].isChecked = result == bit
        }
    }

    private fun updateProblemSize() {
        val position = problemToPosition[activity.preferenceManager.getProblem()]
        position?.let { problemSpinner.setSelection(it) }
    }

    private fun setQuiz(option: QuizOption) {
        radioButton.isChecked = true

        selectedProblem = getRandomProblem(option)

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

    private fun getRandomProblem(quizOption: QuizOption): ProblemData {
        val data = activity.data
        val candidate = arrayListOf<ProblemData>()
        for (i in data) {
            if (i.year in quizOption.years) {
                candidate.add(i)
            }
        }
        return candidate.random()
    }

    private fun getInfoText(problem: ProblemData) =
        StringBuilder("${problem.year}년 ${problem.pid}번").toString()

    private fun isCorrect(pno: Int) = selectedProblem.answer == pno

}