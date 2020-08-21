package com.cpa.cpa_word_problem

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_note.*
import kotlinx.android.synthetic.main.fragment_setting.*

class SettingFragment : Fragment() {

    lateinit var activity: MainActivity
    lateinit var wrongProblemDBHelper: WrongProblemDBHelper
    lateinit var onDBClearedListener: OnDBClearedListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    private fun init() {
        activity = requireActivity() as MainActivity
        wrongProblemDBHelper = WrongProblemDBHelper(activity)
        onDBClearedListener = object: OnDBClearedListener {
            override fun onDBCleared() {
                val fragmentManager = activity.supportFragmentManager
                val noteFragment = fragmentManager.findFragmentByTag("f2") as NoteFragment
                noteFragment.wrongProblemDBHelper.clear()
                noteFragment.wrongProblemList.clear()
                val adapter = (noteFragment.wrongProblemRecyclerView.adapter as WrongProblemAdapter)
                adapter.checked.clear()
                adapter.submitList(noteFragment.wrongProblemList)
            }
        }

        quizLayout1.setOnClickListener {
            val builder = AlertDialog.Builder(activity)
            val spinner = Spinner(activity)
            spinner.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item,
                resources.getStringArray(R.array.problems))

            builder.setMessage("기본 문제 수를 선택하세요.")
                .setCancelable(true)
                .setSpinner(spinner)
                .setPositiveButton("확인") { _, _ ->
                    val problemString = spinner.selectedItem.toString()
                    val problemSize = problemString.replace("[^0-9]".toRegex(), "").toInt()
                    activity.preferenceManager.setProblemSize(problemSize)
                }
                .setNegativeButton("취소") { _, _ -> }
                .create()
                .show()
        }
        quizSettingTextView1.text = getString(R.string.quiz_setting_text, getString(R.string.quiz_problem_size_description))

        quizLayout2.setOnClickListener {
            val checkBoxList = arrayListOf<CheckBox>()
            val builder = AlertDialog.Builder(activity)
            builder.setMessage("기본 출제년도를 선택하세요.")
                .setCancelable(true)
                .setYearCheckbox(checkBoxList)
                .setPositiveButton("확인") { _, _ ->
                    var checkBoxBitSet = 0b00000
                    for (i in checkBoxList.indices) {
                        if (checkBoxList[i].isChecked) {
                            checkBoxBitSet = checkBoxBitSet or (1 shl i)
                        }
                        activity.preferenceManager.setSelectedYear(checkBoxBitSet)
                    }
                }
                .setNegativeButton("취소") { _, _ -> }
                .create()
                .show()
        }

        DBLayout.setOnClickListener {
            val builder = AlertDialog.Builder(activity)
            builder.setMessage("오답노트를 초기화하시겠습니까?")
                .setCancelable(true)
                .setPositiveButton("초기화") { _, _ ->
                    onDBClearedListener.onDBCleared()
                }
                .setNegativeButton("취소") { _, _ -> }
                .create()
                .show()
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

    private fun AlertDialog.Builder.setSpinner(spinner: Spinner): AlertDialog.Builder {
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
        for (year in activity.startYear..activity.endYear) {
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

    interface OnDBClearedListener {
        fun onDBCleared()
    }
}