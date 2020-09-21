package com.cpa.cpa_word_problem

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.cpa.cpa_word_problem.adapters.WrongProblemAdapter
import kotlinx.android.synthetic.main.fragment_note.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteFragment : Fragment() {

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
        val activity = requireActivity() as MainActivity
        val viewModel = activity.viewModel
        wrongProblemRecyclerView.layoutManager = LinearLayoutManager(activity)
        val adapter = WrongProblemAdapter()
        wrongProblemRecyclerView.adapter = adapter

        val problemDao = viewModel.getProblemDao()
        problemDao.getAll().observe(activity, Observer {
            adapter.submitList(it)
        })

        adapter.itemClickListener = object : WrongProblemAdapter.OnItemClickListener {
            override fun onItemClick(
                holder: WrongProblemAdapter.WrongProblemViewHolder,
                position: Int
            ) {
                val problem = adapter.currentList[position]
                val checked = adapter.checked
                checked[problem] = if (!checked.containsKey(problem)) true else !checked[problem]!!
                adapter.itemLookup.lookup(holder, position)
            }
        }
        adapter.itemLongClickListener = object : WrongProblemAdapter.OnItemLongClickListener {
            override fun onItemLongClick(
                holder: WrongProblemAdapter.WrongProblemViewHolder,
                position: Int
            ) {
                setDeleteConfirmDialog(position)
            }

            private fun setDeleteConfirmDialog(position: Int) {
                val builder = AlertDialog.Builder(activity)
                builder.setMessage("해당 문제를 삭제하시겠습니까?")
                    .setCancelable(true)
                    .setPositiveButton("삭제") { _, _ ->
                        lifecycleScope.launch(Dispatchers.IO) {
                            val problem = adapter.currentList.removeAt(position)
                            adapter.checked.remove(problem)
                            problemDao.delete(problem)
                        }
                    }
                    .setNegativeButton("취소") { _, _ -> }
                    .create()
                    .show()
            }
        }
        adapter.itemLookup = object : WrongProblemAdapter.ItemLookup {
            override fun lookup(
                holder: WrongProblemAdapter.WrongProblemViewHolder,
                position: Int
            ) {
                val problem = adapter.currentList[position]
                if (adapter.checked[problem] == true) {
                    unfoldProblem(holder)
                    showProblems(holder, problem.answer)
                } else {
                    foldProblem(holder)
                }
            }
        }

        wrongProblemRecyclerView.adapter = adapter
        wrongProblemRecyclerView.addItemDecoration(
            DividerItemDecoration(
                activity,
                LinearLayoutManager.VERTICAL
            )
        )
    }

    override fun onResume() {
        val activity = requireActivity() as MainActivity
        val viewModel = activity.viewModel
        if (viewModel.isWrongProblemExist()) {
            lifecycleScope.launch(Dispatchers.IO) {
                viewModel.getProblemDao().insertAll(viewModel.getWrongProblemList())
                viewModel.clearWrongProblems()
            }
        }
        super.onResume()
    }

    private fun foldProblem(holder: WrongProblemAdapter.WrongProblemViewHolder) {
        holder.wrongProblemLayout.visibility = View.GONE
    }

    private fun unfoldProblem(holder: WrongProblemAdapter.WrongProblemViewHolder) {
        holder.wrongProblemLayout.visibility = View.VISIBLE
    }

    private fun showProblems(holder: WrongProblemAdapter.WrongProblemViewHolder, answer: Int) {
        val textViewMapper = hashMapOf<Int, TextView>()
        textViewMapper[1] = holder.wrongProblemTextView1
        textViewMapper[2] = holder.wrongProblemTextView2
        textViewMapper[3] = holder.wrongProblemTextView3
        textViewMapper[4] = holder.wrongProblemTextView4
        textViewMapper[5] = holder.wrongProblemTextView5

        val defaultTypeface = Typeface.DEFAULT
        val defaultColor =
            ContextCompat.getColor(requireActivity(), android.R.color.tab_indicator_text)
        for (i in 1..5) {
            textViewMapper[i]?.let { setProblemTextColor(it, defaultTypeface, defaultColor) }
        }

        val highlightTypeface = Typeface.DEFAULT_BOLD
        val highlightColor = ContextCompat.getColor(requireActivity(), R.color.colorPrimary)
        textViewMapper[answer]?.let { setProblemTextColor(it, highlightTypeface, highlightColor) }
    }

    private fun setProblemTextColor(textView: TextView, typeface: Typeface, color: Int) {
        textView.typeface = typeface
        textView.setTextColor(color)
    }

}