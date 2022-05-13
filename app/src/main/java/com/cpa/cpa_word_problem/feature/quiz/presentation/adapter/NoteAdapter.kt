package com.cpa.cpa_word_problem.feature.quiz.presentation.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cpa.cpa_word_problem.databinding.ListItemProblemBinding
import com.ysshin.cpaquiz.domain.model.Problem
import com.cpa.cpa_word_problem.feature.quiz.presentation.model.UserSolvedProblemModel
import com.ysshin.cpaquiz.shared.base.Consumer
import com.ysshin.cpaquiz.shared.android.util.inflate

class NoteAdapter :
    ListAdapter<UserSolvedProblemModel, NoteAdapter.ProblemViewHolder>(UserSolvedProblemDiffCallback()) {

    var isShowing = true
    var onProblemClick: Consumer<Problem> = {}
    var onProblemLongClick: Consumer<Problem> = {}

    override fun getItemCount() = if (isShowing) super.getItemCount() else 0

    class ProblemViewHolder(private val binding: ListItemProblemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var onProblemClick: Consumer<Problem> = {}
        var onProblemLongClick: Consumer<Problem> = {}

        init {
            binding.setOnClickListener {
                binding.problem?.let { problem ->
                    onProblemClick(problem)
                }
            }

            binding.setOnLongClickListener {
                binding.problem?.let { problem ->
                    onProblemLongClick(problem)
                }
                true
            }
        }

        fun bind(userSolvedProblem: UserSolvedProblemModel) {
            binding.problem = userSolvedProblem.problem
            binding.elapsedTime = userSolvedProblem.elapsedTime
            binding.userSelectedIndex = userSolvedProblem.userSelectedIndex
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ProblemViewHolder(
        parent.inflate(ListItemProblemBinding::inflate)
    ).also { viewHolder ->
        viewHolder.onProblemClick = onProblemClick
        viewHolder.onProblemLongClick = onProblemLongClick
    }

    override fun onBindViewHolder(holder: ProblemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

private class UserSolvedProblemDiffCallback : DiffUtil.ItemCallback<UserSolvedProblemModel>() {

    override fun areItemsTheSame(oldItem: UserSolvedProblemModel, newItem: UserSolvedProblemModel) =
        (oldItem.problem == newItem.problem)

    override fun areContentsTheSame(
        oldItem: UserSolvedProblemModel,
        newItem: UserSolvedProblemModel
    ) = areItemsTheSame(oldItem, newItem)
}

@SuppressLint("NotifyDataSetChanged")
fun NoteAdapter.show() {
    isShowing = true
    notifyDataSetChanged()
}

@SuppressLint("NotifyDataSetChanged")
fun NoteAdapter.hide() {
    isShowing = false
    notifyDataSetChanged()
}
