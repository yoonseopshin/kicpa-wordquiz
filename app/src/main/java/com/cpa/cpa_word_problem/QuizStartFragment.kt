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
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.cpa.cpa_word_problem.data.ProblemData
import com.cpa.cpa_word_problem.data.QuizOption
import com.cpa.cpa_word_problem.data.QuizState
import com.cpa.cpa_word_problem.utils.QuizClassifier
import kotlinx.android.synthetic.main.fragment_quiz_start.*
import kotlinx.android.synthetic.main.toast_success_view.view.*
import kotlinx.android.synthetic.main.toast_wrong_view.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class QuizStartFragment : Fragment() {

    private lateinit var selectedProblem: ProblemData
    private lateinit var toastSuccessLayout: View
    private lateinit var toastWrongLayout: View
    private var isEnd = false

    companion object {
        const val DURATION = 500L
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz_start, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        createToastLayout()
        setSubmitButtonListener()
    }

    private fun setSubmitButtonListener() {
        val activity = requireActivity() as MainActivity
        val viewModel = activity.viewModel
        val wrongProblems = arrayListOf<ProblemData>()

        setQuiz(viewModel.quizOption)

        submitBtn.setOnClickListener {
            if (isEnd) return@setOnClickListener

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

            viewModel.turn = viewModel.probSize.coerceAtMost(viewModel.turn + 1)

            if (viewModel.turn == viewModel.probSize) {
                isEnd = true
                Handler().postDelayed({
                    viewModel.addWrongProblems(wrongProblems)
                    setFragment(QuizState.Ready)
                }, DURATION)
            } else {
                setQuiz(viewModel.quizOption)
            }
        }
    }

    private fun createToastLayout() {
        val nullParent = null
        toastSuccessLayout = layoutInflater.inflate(R.layout.toast_success_view, nullParent)
        toastSuccessLayout.successToastTextView.text = getString(R.string.success)
        toastWrongLayout = layoutInflater.inflate(R.layout.toast_wrong_view, nullParent)
        toastWrongLayout.wrongToastTextView.text = getString(R.string.wrong)
    }

    private fun isDarkTheme(activity: Activity): Boolean {
        return activity.resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
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
            quizStartWholeLayout,
            "backgroundColor",
            animColor,
            backgroundColor
        ).apply {
            duration = DURATION
            start()
        }
    }

    private fun setQuiz(option: QuizOption) {
        val activity = requireActivity() as MainActivity
        val viewModel = activity.viewModel
        radioButton.isChecked = true

        selectedProblem = viewModel.getRandomProblem(option)

        val (description, subDescription) = QuizClassifier
            .classify(selectedProblem.description)
        descriptionTextView.text = description
        if (subDescription.isEmpty()) subDescriptionTextView.visibility = View.GONE
        else subDescriptionTextView.visibility = View.VISIBLE
        subDescriptionTextView.text = subDescription
        problemCategoryTextView.text = option.type
        problemInfoTextView.text = getInfoText(selectedProblem)
        radioButton.text = selectedProblem.p1
        radioButton2.text = selectedProblem.p2
        radioButton3.text = selectedProblem.p3
        radioButton4.text = selectedProblem.p4
        radioButton5.text = selectedProblem.p5
    }

    override fun onPause() {
        super.onPause()
        setFragment(QuizState.Ready)
    }

    override fun onResume() {
        super.onResume()
        isEnd = false
        val viewModel = (requireActivity() as MainActivity).viewModel
        viewModel.turn = 0
    }

    private fun getInfoText(problem: ProblemData) =
        StringBuilder("${problem.year}년 ${problem.pid}번").toString()

    private fun isCorrect(pno: Int) = selectedProblem.answer == pno

    private fun setFragment(quizState: QuizState) {
        (requireParentFragment() as QuizFragment).setFragment(quizState)
    }
}