package com.cpa.cpa_word_problem.feature.quiz.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cpa.cpa_word_problem.R
import com.cpa.cpa_word_problem.databinding.LayoutResultTimerBinding
import com.cpa.cpa_word_problem.utils.TimeFormatter
import com.cpa.cpa_word_problem.utils.color
import com.cpa.cpa_word_problem.utils.gone
import com.cpa.cpa_word_problem.utils.visible

class TimerAdapter : RecyclerView.Adapter<TimerAdapter.ItemViewHolder>() {

    var timesPerProblem: List<Int> = emptyList()

    class ItemViewHolder(private val binding: LayoutResultTimerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            val context = binding.root.context
            binding.btnTimer.backgroundTintList =
                context.color(R.color.timer_result_highlight_color)
            binding.root.setCardBackgroundColor(context.color(R.color.timer_result_highlight_color_0_20))
        }

        fun bind(timesPerProblem: List<Int>) {
            val totalElapsedTime = timesPerProblem.sum()
            if (totalElapsedTime > 0) {
                binding.root.visible()
                binding.tvTotalTime.text = TimeFormatter.formatKorean(totalElapsedTime)
            } else {
                binding.root.gone()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ItemViewHolder(
        LayoutResultTimerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(timesPerProblem)
    }

    override fun getItemCount() = if (timesPerProblem.sum() > 0) 1 else 0

}