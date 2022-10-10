package com.ysshin.cpaquiz.feature.quiz.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ysshin.cpaquiz.feature.quiz.databinding.ListItemSubDescriptionBinding
import com.ysshin.cpaquiz.core.android.util.inflate

class SubDescriptionAdapter :
    ListAdapter<String, SubDescriptionAdapter.DescriptionViewHolder>(DescriptionDiffCallback()) {

    class DescriptionViewHolder(private val binding: ListItemSubDescriptionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(description: String) {
            binding.lineStart = ""
            binding.description = description

            if (description.length <= indentRegexes.maxOf { it.minCheckDigit }) {
                binding.executePendingBindings()
                return
            }

            for (indentRegex in indentRegexes) {
                if (indentRegex.isMatch(description)) {
                    binding.lineStart = indentRegex.head(description)
                    binding.description = indentRegex.tail(description)
                    break
                }
            }

            binding.executePendingBindings()
        }

        data class RegexWithCheckDigit(val regex: Regex, val minCheckDigit: Int) {
            fun head(text: String) = text.substring(0, minCheckDigit)
            fun tail(text: String) = text.substring(minCheckDigit)
            fun isMatch(text: String) = regex.matches(head(text))
        }

        companion object {
            private val alphabetIndentRegex = RegexWithCheckDigit("[A-Za-z]\\. ".toRegex(), 3)
            private val koreanIndentRegex = RegexWithCheckDigit("[ㄱ-ㅎ]\\. ".toRegex(), 3)
            private val specialKoreanIndentRegex = RegexWithCheckDigit("[㉠-㉭] ".toRegex(), 2)
            private val indentRegexes =
                listOf(alphabetIndentRegex, koreanIndentRegex, specialKoreanIndentRegex)
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
