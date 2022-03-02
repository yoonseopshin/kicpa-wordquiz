package com.cpa.cpa_word_problem.feature.quiz.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cpa.cpa_word_problem.databinding.ListItemProblemBinding
import com.cpa.cpa_word_problem.feature.quiz.domain.model.Problem
import com.cpa.cpa_word_problem.feature.quiz.presentation.model.UserSolvedProblemModel

class NoteAdapter :
    ListAdapter<UserSolvedProblemModel, NoteAdapter.ProblemViewHolder>(UserSolvedProblemDiffCallback()) {

    var isShowing = true
    var onProblemClick: ((Problem) -> Unit)? = null
    var onProblemLongClick: ((Problem) -> Unit)? = null

    override fun getItemCount() = if (isShowing) super.getItemCount() else 0

    class ProblemViewHolder(
        private val binding: ListItemProblemBinding,
        onProblemClick: ((Problem) -> Unit)? = null,
        onProblemLongClick: ((Problem) -> Unit)? = null,
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setOnClickListener {
                binding.problem?.let { problem ->
                    onProblemClick?.invoke(problem)
                }
            }

            binding.setOnLongClickListener {
                binding.problem?.let { problem ->
                    onProblemLongClick?.invoke(problem)
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
        ListItemProblemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ),
        onProblemClick,
        onProblemLongClick
    )

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

fun NoteAdapter.show() {
    isShowing = true
    notifyDataSetChanged()
}

fun NoteAdapter.hide() {
    isShowing = false
    notifyDataSetChanged()
}

fun NoteAdapter.showOrHide(shouldBeShowing: Boolean) {
    isShowing = shouldBeShowing
    notifyDataSetChanged()
}
