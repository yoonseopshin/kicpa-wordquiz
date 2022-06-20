package com.ysshin.cpaquiz.feature.quiz.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ysshin.cpaquiz.feature.quiz.databinding.ListItemSubDescriptionBinding
import com.ysshin.cpaquiz.shared.android.util.inflate

class SubDescriptionAdapter :
    ListAdapter<String, SubDescriptionAdapter.DescriptionViewHolder>(DescriptionDiffCallback()) {

    class DescriptionViewHolder(private val binding: ListItemSubDescriptionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(description: String) {
            if (description.length <= 3) {
                binding.description = description
                binding.executePendingBindings()
                return
            }

            val lineStartText = description.substring(0, 3)

            when {
                ALPHABET_INDENT_REGEX.matches(lineStartText) ||
                    HANGUL_INDEX_REGEX.matches(lineStartText) ||
                    HANGUL_INDEX_REGEX_2.matches(lineStartText) -> {
                    binding.lineStart = lineStartText
                    binding.description = description.substring(3)
                }
                else -> {
                    binding.lineStart = ""
                    binding.description = description
                }
            }

            binding.executePendingBindings()
        }

        companion object {
            private val ALPHABET_INDENT_REGEX = "[A-Za-z]\\. ".toRegex()
            private val HANGUL_INDEX_REGEX = "[ㄱ-ㅎ]\\. ".toRegex()
            private val HANGUL_INDEX_REGEX_2 = "[㉠-㉭] .".toRegex()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DescriptionViewHolder(
        parent.inflate(ListItemSubDescriptionBinding::inflate)
    )

    override fun onBindViewHolder(holder: DescriptionViewHolder, position: Int) =
        holder.bind(getItem(position))
}

private class DescriptionDiffCallback : DiffUtil.ItemCallback<String>() {

    override fun areItemsTheSame(oldItem: String, newItem: String) = oldItem == newItem

    override fun areContentsTheSame(oldItem: String, newItem: String) = areItemsTheSame(oldItem, newItem)
}
