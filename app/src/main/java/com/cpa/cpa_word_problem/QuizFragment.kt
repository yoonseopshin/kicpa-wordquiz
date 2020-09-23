package com.cpa.cpa_word_problem

import android.animation.ObjectAnimator
import android.app.Activity
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.view.iterator
import androidx.fragment.app.Fragment
import com.cpa.cpa_word_problem.data.ProblemData
import com.cpa.cpa_word_problem.data.QuizOption
import kotlinx.android.synthetic.main.fragment_quiz.*
import kotlinx.android.synthetic.main.toast_success_view.view.*
import kotlinx.android.synthetic.main.toast_wrong_view.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class QuizFragment : Fragment() {

    private lateinit var selectedProblem: ProblemData
    private lateinit var quizOption: QuizOption
    private lateinit var toastSuccessLayout: View
    private lateinit var toastWrongLayout: View
    private val checkBoxList = arrayListOf<CheckBox>()
    private var curCheckBoxHorizontalLayout: LinearLayout? = null

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
        setInfoVisibility(View.VISIBLE)
        setProblemVisibility(View.GONE)
        setButtonListener()
        setProblemSpinnerAdapter()
        setCheckBoxOnCardView()
        createToastLayout()
    }

    private fun setCheckBoxOnCardView() {
        val activity = requireActivity() as MainActivity
        val viewModel = activity.viewModel
        cardView.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val cardViewWidth = cardView.measuredWidth

        for (year in viewModel.startYear..viewModel.endYear) {
            if (curCheckBoxHorizontalLayout == null) {
                curCheckBoxHorizontalLayout = LinearLayout(activity)
                checkBoxVerticalLayout.addView(curCheckBoxHorizontalLayout)
            }

            val checkBox = CheckBox(activity)
            checkBox.text = year.toString()
            checkBox.isChecked = true
            checkBox.tag = year
            checkBox.measure(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            checkBoxList.add(checkBox)
            val checkBoxWidth = checkBox.measuredWidth
            curCheckBoxHorizontalLayout?.measure(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            val curCheckBoxLayoutWidth = curCheckBoxHorizontalLayout?.measuredWidth ?: 0

            if (curCheckBoxLayoutWidth + checkBoxWidth >= cardViewWidth) {
                curCheckBoxHorizontalLayout = LinearLayout(activity)
                checkBoxVerticalLayout.addView(curCheckBoxHorizontalLayout)
            }

            curCheckBoxHorizontalLayout?.addView(checkBox)
        }
    }

    private fun createToastLayout() {
        toastSuccessLayout = layoutInflater.inflate(R.layout.toast_success_view, null)
        toastSuccessLayout.successToastTextView.text = getString(R.string.success)
        toastWrongLayout = layoutInflater.inflate(R.layout.toast_wrong_view, null)
        toastWrongLayout.wrongToastTextView.text = getString(R.string.wrong)
    }

    private fun setProblemSpinnerAdapter() {
        problemSpinner.adapter = ArrayAdapter(
            requireContext(), R.layout.spinner_item,
            resources.getStringArray(R.array.problems)
        )
    }

    private fun setButtonListener() {
        val activity = requireActivity() as MainActivity
        val viewModel = activity.viewModel
        val wrongProblems = arrayListOf<ProblemData>()
        quizBtn.setOnClickListener {
            val years = arrayListOf<Int>()
            for (year in viewModel.startYear..viewModel.endYear) {
                for (checkBoxHorizontalLayout in checkBoxVerticalLayout) {
                    val checkBox =
                        checkBoxHorizontalLayout.findViewWithTag<CheckBox>(year) ?: continue
                    if (checkBox.isChecked) {
                        years.add(year)
                    }
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
        }

        submitBtn.setOnClickListener {
            val radioBtn =
                radioGroup.findViewById<View>(radioGroup.checkedRadioButtonId) as RadioButton
            val pno = radioGroup.indexOfChild(radioBtn) + 1

            val backgroundColor = ContextCompat.getColor(
                activity,
                if (isDarkTheme(activity)) android.R.color.background_dark
                else android.R.color.background_light
            )

            if (isCorrect(pno)) {
                if (viewModel.isQuizEffectOn()) {
                    val animColor = ContextCompat.getColor(activity, R.color.success)
                    showAnswerByAnimation(backgroundColor, animColor)
                } else {
                    showAnswerByToast(true)
                }
            } else {
                if (viewModel.isQuizEffectOn()) {
                    val animColor = ContextCompat.getColor(activity, R.color.wrong)
                    showAnswerByAnimation(backgroundColor, animColor)
                } else {
                    showAnswerByToast(false)
                }
                wrongProblems.add(selectedProblem)
            }

            if (viewModel.turn++ >= viewModel.probSize) {
                Handler().postDelayed({
                    setProblemVisibility(View.GONE)
                    setInfoVisibility(View.VISIBLE)
                    viewModel.addWrongProblems(wrongProblems)
                }, DURATION)
            } else {
                setQuiz(quizOption)
            }
        }
    }

    private fun isDarkTheme(activity: Activity): Boolean {
        return activity.resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
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

    private fun showAnswerByToast(isQuizEffectOn: Boolean) {
        val activity = requireActivity()
        val toast = Toast(activity)
        if (isQuizEffectOn) {
            toast.view = toastSuccessLayout
        } else {
            toast.view = toastWrongLayout
        }
        toast.setGravity(Gravity.BOTTOM or Gravity.CENTER, 0, 200)
        toast.duration = Toast.LENGTH_SHORT
        toast.show()

        GlobalScope.launch {
            delay(DURATION)
            toast.cancel()
        }
    }

    private fun showAnswerByAnimation(backgroundColor: Int, animColor: Int) {
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
        val position = viewModel.problemToPosition[viewModel.getSelectedProblemSize()]
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