package com.cpa.cpa_word_problem

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.fragment_setting.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    override fun onPause() {
        super.onPause()
        val activity = requireActivity() as MainActivity
        val viewModel = activity.viewModel
        viewModel.setQuizEffect(quizEffectSwitch.isChecked)
    }

    override fun onResume() {
        super.onResume()
        val activity = requireActivity() as MainActivity
        val viewModel = activity.viewModel
        quizEffectSwitch.isChecked = viewModel.isQuizEffectOn()
    }

    private fun init() {
        val activity = requireActivity() as MainActivity

        quizLayout1.setOnClickListener {
            setDefaultProblemConfirmDialog(activity)
        }

        quizSettingTextView1.text =
            getString(R.string.quiz_setting_text, getString(R.string.quiz_problem_size_description))

        quizLayout2.setOnClickListener {
            setDefaultYearConfirmDialog(activity)
        }

        DBLayout.setOnClickListener {
            setDbInitConfirmDialog(activity)
        }

        emailLayout.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/email"
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.email)))
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject))
            intent.type = "message/rfc822"
            startActivity(Intent.createChooser(intent, "개발자한테 문의하기"))
        }
    }

    private fun setDbInitConfirmDialog(activity: MainActivity) {
        val builder = AlertDialog.Builder(
            activity,
            R.style.AlertDialogStyle
        )
        builder.setMessage("오답노트를 초기화하시겠습니까?")
            .setCancelable(true)
            .setPositiveButton("초기화") { _, _ ->
                lifecycleScope.launch(Dispatchers.IO) {
                    val viewModel = activity.viewModel
                    viewModel.getProblemDao().deleteAll()
                }
            }
            .setNegativeButton("취소") { _, _ -> }
            .create()
            .show()
    }

    private fun setDefaultYearConfirmDialog(activity: MainActivity) {
        val checkBoxList = arrayListOf<CheckBox>()
        val builder = AlertDialog.Builder(
            activity, R.style.AlertDialogStyle
        )
        builder.setMessage("기본 출제년도를 선택하세요.")
            .setCancelable(true)
            .setYearCheckbox(checkBoxList)
            .setPositiveButton("확인") { _, _ ->
                var checkBoxBitSet = 0b00000
                for (i in checkBoxList.indices) {
                    if (checkBoxList[i].isChecked) {
                        checkBoxBitSet = checkBoxBitSet or (1 shl i)
                    }
                    val viewModel = activity.viewModel
                    viewModel.setSelectedYear(checkBoxBitSet)
                }
            }
            .setNegativeButton("취소") { _, _ -> }
            .create()
            .show()
    }

    private fun setDefaultProblemConfirmDialog(activity: MainActivity) {
        val builder = AlertDialog.Builder(
            activity, R.style.AlertDialogStyle
        )
        val spinner = Spinner(activity)
        spinner.adapter = ArrayAdapter(
            requireContext(), R.layout.spinner_item,
            resources.getStringArray(R.array.problems)
        )

        builder.setMessage("기본 문제 수를 선택하세요.")
            .setCancelable(true)
            .setSpinner(spinner)
            .setPositiveButton("확인") { _, _ ->
                val problemString = spinner.selectedItem.toString()
                val problemSize = problemString.replace("[^0-9]".toRegex(), "").toInt()
                val viewModel = activity.viewModel
                viewModel.setProblemSize(problemSize)
            }
            .setNegativeButton("취소") { _, _ -> }
            .create()
            .show()
    }

    private fun AlertDialog.Builder.setSpinner(spinner: Spinner): AlertDialog.Builder {
        val activity = requireActivity()
        val container = FrameLayout(activity)
        container.addView(spinner)
        val containerParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        val marginHorizontal = 48f
        val marginTop = 16f
        containerParams.topMargin = (marginTop / 2).toInt()
        containerParams.leftMargin = marginHorizontal.toInt()
        containerParams.rightMargin = marginHorizontal.toInt()
        container.layoutParams = containerParams
        val superContainer = FrameLayout(requireContext())
        superContainer.addView(container)
        setView(superContainer)
        return this
    }

    private fun AlertDialog.Builder.setYearCheckbox(checkBoxList: ArrayList<CheckBox>): AlertDialog.Builder {
        val activity = requireActivity() as MainActivity
        val viewModel = activity.viewModel
        for (year in viewModel.startYear..viewModel.endYear) {
            val checkBox = CheckBox(activity)
            checkBox.text = year.toString()
            checkBox.isChecked = true
            checkBox.tag = year
            checkBoxList.add(checkBox)
        }

        val container = LinearLayout(activity)
        for (checkBox in checkBoxList) {
            container.addView(checkBox)
        }
        val containerParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        val marginHorizontal = 48f
        val marginTop = 16f
        containerParams.topMargin = (marginTop / 2).toInt()
        containerParams.leftMargin = marginHorizontal.toInt()
        containerParams.rightMargin = marginHorizontal.toInt()
        container.layoutParams = containerParams
        container.orientation = LinearLayout.VERTICAL
        val superContainer = FrameLayout(requireContext())
        superContainer.addView(container)
        setView(superContainer)
        return this
    }

}