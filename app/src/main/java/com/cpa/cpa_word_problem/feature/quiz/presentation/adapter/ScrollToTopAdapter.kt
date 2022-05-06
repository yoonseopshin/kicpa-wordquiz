package com.cpa.cpa_word_problem.feature.quiz.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cpa.cpa_word_problem.databinding.LayoutScrollToTopBinding

class ScrollToTopAdapter : RecyclerView.Adapter<ScrollToTopAdapter.ItemViewHolder>() {

    var isShowing = true
    var onScrollToTopClick: (() -> Unit)? = null

    class ItemViewHolder(
        private val binding: LayoutScrollToTopBinding,
        onScrollToTopClick: (() -> Unit)? = null
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setOnClickListener {
                onScrollToTopClick?.invoke()
            }
        }

        fun bind() {
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ItemViewHolder(
        LayoutScrollToTopBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        onScrollToTopClick
    )

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = if (isShowing) 1 else 0

}

fun ScrollToTopAdapter.show() {
    isShowing = true
    notifyDataSetChanged()
}

fun ScrollToTopAdapter.hide() {
    isShowing = false
    notifyDataSetChanged()
}

fun ScrollToTopAdapter.showOrHide(shouldBeShowing: Boolean) {
    isShowing = shouldBeShowing
    notifyDataSetChanged()
}
