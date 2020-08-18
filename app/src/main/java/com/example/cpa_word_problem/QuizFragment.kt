package com.example.cpa_word_problem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_quiz.*

class QuizFragment : Fragment() {

    lateinit var activity: MainActivity
    lateinit var selectedProblem: AccountingData
    lateinit var quizOption : QuizOption
    var turn = 1
    var problemSize = 0
    lateinit var wrongProblems : ArrayList<AccountingData>

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
            val radioBtn = radioGroup.findViewById<View>(radioGroup.checkedRadioButtonId) as RadioButton
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

        problemSpinner.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item,
            resources.getStringArray(R.array.problems))
        val position = when (activity.preferenceManager.getProblem()) {
            3 -> 0
            5 -> 1
            7 -> 2
            10 -> 3
            else -> 0
        }
        problemSpinner.setSelection(position)

        for (year in activity.startYear..activity.endYear) {
            val checkBox = CheckBox(activity)
            checkBox.text = year.toString()
            checkBox.isChecked = true
            checkBox.tag = year
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
    }

    private fun setQuiz(option: QuizOption) {
        radioButton.isChecked = true

        val data = activity.data
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

    private fun getRandomProblem(quizOption: QuizOption) : AccountingData {
        val data = activity.data
        val candidate = arrayListOf<AccountingData>()
        for (i in data) {
            if (i.year in quizOption.years) {
                candidate.add(i)
            }
        }
        return candidate.random()
    }

    private fun getInfoText(problem : AccountingData) = StringBuilder("${problem.year}년 ${problem.pid}번").toString()

    private fun isCorrect(pno: Int) = selectedProblem.answer == pno

}