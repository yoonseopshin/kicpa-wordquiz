package com.sys0927.cpawordproblem.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sys0927.cpawordproblem.R
import com.sys0927.cpawordproblem.data.ProblemData
import com.sys0927.cpawordproblem.data.ProblemType
import com.sys0927.cpawordproblem.utils.QuizClassifier

class WrongProblemAdapter :
    ListAdapter<ProblemData, WrongProblemAdapter.WrongProblemViewHolder>(DiffCallback) {

    lateinit var itemClickListener: OnItemClickListener
    lateinit var itemLongClickListener: OnItemLongClickListener
    lateinit var itemLookup: ItemLookup
    val checked = HashMap<ProblemData, Boolean>()

    object DiffCallback : DiffUtil.ItemCallback<ProblemData>() {
        override fun areItemsTheSame(oldItem: ProblemData, newItem: ProblemData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ProblemData, newItem: ProblemData): Boolean {
            return areItemsTheSame(oldItem, newItem)
        }
    }

    inner class WrongProblemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wrongProblemInfoTextView: TextView =
            itemView.findViewById(R.id.wrongProblemInfoTextView)
        val wrongProblemCategoryTextView: TextView =
            itemView.findViewById(R.id.wrongProblemCategoryTextView)
        val wrongProblemDescriptionTextView: TextView =
            itemView.findViewById(R.id.wrongProblemDescriptionTextView)
        val wrongProblemSubDescriptionTextView: TextView =
            itemView.findViewById(R.id.wrongProblemSubDescriptionTextView)
        val wrongProblemLayout: LinearLayout = itemView.findViewById(R.id.wrongProblemLayout)
        val wrongProblemTextView1: TextView = itemView.findViewById(R.id.wrongProblemTextView1)
        val wrongProblemTextView2: TextView = itemView.findViewById(R.id.wrongProblemTextView2)
        val wrongProblemTextView3: TextView = itemView.findViewById(R.id.wrongProblemTextView3)
        val wrongProblemTextView4: TextView = itemView.findViewById(R.id.wrongProblemTextView4)
        val wrongProblemTextView5: TextView = itemView.findViewById(R.id.wrongProblemTextView5)

        init {
            itemView.setOnClickListener {
                itemClickListener.onItemClick(this, adapterPosition)
            }

            itemView.setOnLongClickListener {
                itemLongClickListener.onItemLongClick(this, adapterPosition)
                true
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(holder: WrongProblemViewHolder, position: Int)
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(holder: WrongProblemViewHolder, position: Int)
    }

    interface ItemLookup {
        fun lookup(holder: WrongProblemViewHolder, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WrongProblemViewHolder {
        return WrongProblemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.problem_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: WrongProblemViewHolder, position: Int) {
        val problem = currentList[position]
        holder.wrongProblemInfoTextView.text =
            StringBuilder("${problem.year}년 ${problem.pid}번").toString()
        holder.wrongProblemCategoryTextView.text = translate(problem.type)
        val (description, subDescription) = QuizClassifier
            .classify(problem.description)
        if (subDescription.isEmpty()) holder.wrongProblemSubDescriptionTextView.visibility =
            View.GONE
        else holder.wrongProblemSubDescriptionTextView.visibility = View.VISIBLE
        holder.wrongProblemDescriptionTextView.text = description
        holder.wrongProblemSubDescriptionTextView.text = subDescription
        holder.wrongProblemTextView1.text = problem.p1
        holder.wrongProblemTextView2.text = problem.p2
        holder.wrongProblemTextView3.text = problem.p3
        holder.wrongProblemTextView4.text = problem.p4
        holder.wrongProblemTextView5.text = problem.p5
        itemLookup.lookup(holder, position)
    }

    override fun submitList(list: List<ProblemData>?) {
        for (accountingData in currentList) {
            checked.putIfAbsent(accountingData, false)
        }
        return super.submitList(list?.let { ArrayList<ProblemData>(it) })
    }

    private fun translate(word: String): String {
        return when (word) {
            ProblemType.Accounting.toString() -> "회계학"
            ProblemType.Business.toString() -> "경영학"
            else -> "회계학"
        }
    }
}