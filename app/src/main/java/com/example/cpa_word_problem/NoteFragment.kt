package com.example.cpa_word_problem

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_note.*

class NoteFragment : Fragment() {

    lateinit var activity : MainActivity
    lateinit var wrongProblemDBHelper : WrongProblemDBHelper
    lateinit var wrongProblemList : ArrayList<AccountingData>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_note, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    private fun init() {
        activity = requireActivity() as MainActivity
        wrongProblemDBHelper = activity.wrongProblemDBHelper

        wrongProblemList = wrongProblemDBHelper.fetch()
        wrongProblemRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        val adapter = WrongProblemAdapter()
        adapter.submitList(wrongProblemList)
        adapter.itemClickListener = object: WrongProblemAdapter.OnItemClickListener {
            override fun onItemClick(holder: WrongProblemAdapter.WrongProblemViewHolder, position: Int) {
                val problem = wrongProblemList[position]
                val checked = adapter.checked
                if (!checked.containsKey(problem)) {
                    checked[problem] = true
                } else {
                    checked[problem] = !checked[problem]!!
                }

                if (checked[problem] == true) {
                    visualize(holder, problem.answer)
                } else {
                    holder.wrongProblemLayout.visibility = View.GONE
                }
            }
        }
        adapter.itemLongClickListener = object: WrongProblemAdapter.OnItemLongClickListener {
            override fun onItemLongClick(holder: WrongProblemAdapter.WrongProblemViewHolder, position: Int) {
                val builder = AlertDialog.Builder(activity)
                builder.setMessage("해당 문제를 삭제하시겠습니까?")
                    .setCancelable(true)
                    .setPositiveButton("삭제") { _, _ ->
                        val problem = wrongProblemList.removeAt(position)
                        adapter.checked.remove(problem)
                        wrongProblemDBHelper.remove(problem)
                        adapter.submitList(wrongProblemList)
                    }
                    .setNegativeButton("취소") { _, _ -> }
                    .create()
                    .show()
            }
        }
        adapter.itemLookup = object: WrongProblemAdapter.ItemLookup {
            override fun lookup(
                holder: WrongProblemAdapter.WrongProblemViewHolder,
                position: Int
            ) {
                val problem = wrongProblemList[position]
                if (adapter.checked[problem] == true) {
                    visualize(holder, problem.answer)
                } else {
                    holder.wrongProblemLayout.visibility = View.GONE
                }
            }
        }

        wrongProblemRecyclerView.adapter = adapter
        wrongProblemRecyclerView.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
    }

    override fun onResume() {
        if (activity.wrongProblems.isNotEmpty()) {
            wrongProblemDBHelper.save(activity.wrongProblems)
            activity.wrongProblems.clear()
            wrongProblemList = wrongProblemDBHelper.fetch()
            (wrongProblemRecyclerView.adapter as WrongProblemAdapter).submitList(wrongProblemList)
        }
        super.onResume()
    }

    fun visualize(holder: WrongProblemAdapter.WrongProblemViewHolder, answer: Int) {
        holder.wrongProblemLayout.visibility = View.VISIBLE
        holder.wrongProblemTextView1.typeface = Typeface.DEFAULT
        holder.wrongProblemTextView1.setTextColor(ContextCompat.getColor(activity, android.R.color.tab_indicator_text))
        holder.wrongProblemTextView2.typeface = Typeface.DEFAULT
        holder.wrongProblemTextView2.setTextColor(ContextCompat.getColor(activity, android.R.color.tab_indicator_text))
        holder.wrongProblemTextView3.typeface = Typeface.DEFAULT
        holder.wrongProblemTextView3.setTextColor(ContextCompat.getColor(activity, android.R.color.tab_indicator_text))
        holder.wrongProblemTextView4.typeface = Typeface.DEFAULT
        holder.wrongProblemTextView4.setTextColor(ContextCompat.getColor(activity, android.R.color.tab_indicator_text))
        holder.wrongProblemTextView5.typeface = Typeface.DEFAULT
        holder.wrongProblemTextView5.setTextColor(ContextCompat.getColor(activity, android.R.color.tab_indicator_text))
        when (answer) {
            1 -> {
                holder.wrongProblemTextView1.setTypeface(null, Typeface.BOLD)
                holder.wrongProblemTextView1.setTextColor(ContextCompat.getColor(activity, R.color.colorPrimaryDark))
            }
            2 -> {
                holder.wrongProblemTextView2.setTypeface(null, Typeface.BOLD)
                holder.wrongProblemTextView2.setTextColor(ContextCompat.getColor(activity, R.color.colorPrimaryDark))
            }
            3 -> {
                holder.wrongProblemTextView3.setTypeface(null, Typeface.BOLD)
                holder.wrongProblemTextView3.setTextColor(ContextCompat.getColor(activity, R.color.colorPrimaryDark))
            }
            4 -> {
                holder.wrongProblemTextView4.setTypeface(null, Typeface.BOLD)
                holder.wrongProblemTextView4.setTextColor(ContextCompat.getColor(activity, R.color.colorPrimaryDark))
            }
            5 -> {
                holder.wrongProblemTextView5.setTypeface(null, Typeface.BOLD)
                holder.wrongProblemTextView5.setTextColor(ContextCompat.getColor(activity, R.color.colorPrimaryDark))
            }
        }
    }

}