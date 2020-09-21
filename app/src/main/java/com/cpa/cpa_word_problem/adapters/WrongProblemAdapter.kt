package com.cpa.cpa_word_problem.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cpa.cpa_word_problem.R
import com.cpa.cpa_word_problem.data.ProblemData

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
        val descriptionTextView: TextView =
            itemView.findViewById(R.id.wrongProblemDescriptionTextView)
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
        holder.descriptionTextView.text = problem.description
        holder.wrongProblemTextView1.text = problem.p1
        holder.wrongProblemTextView2.text = problem.p2
        holder.wrongProblemTextView3.text = problem.p3
        holder.wrongProblemTextView4.text = problem.p4
        holder.wrongProblemTextView5.text = problem.p5

        if (checked[problem] == true) {
            itemLookup.lookup(holder, position)
        } else {
            holder.wrongProblemLayout.visibility = View.GONE
        }
    }

    override fun submitList(list: List<ProblemData>?) {
        for (accountingData in currentList) {
            checked.putIfAbsent(accountingData, false)
        }
        return super.submitList(list?.let { ArrayList<ProblemData>(it) })
    }
}