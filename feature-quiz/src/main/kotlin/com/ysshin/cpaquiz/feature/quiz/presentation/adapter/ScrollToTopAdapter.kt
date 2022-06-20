package com.ysshin.cpaquiz.feature.quiz.presentation.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ysshin.cpaquiz.feature.quiz.databinding.LayoutScrollToTopBinding
import com.ysshin.cpaquiz.shared.android.util.inflate
import com.ysshin.cpaquiz.shared.base.Action

class ScrollToTopAdapter : RecyclerView.Adapter<ScrollToTopAdapter.ItemViewHolder>() {

    var isShowing = true
    var onScrollToTopClick: Action = {}

    class ItemViewHolder(private val binding: LayoutScrollToTopBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var onScrollToTopClick: Action = {}

        init {
            binding.setOnClickListener {
                onScrollToTopClick()
            }
        }

        fun bind() {
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ItemViewHolder(
        parent.inflate(LayoutScrollToTopBinding::inflate)
    ).also { viewHolder ->
        viewHolder.onScrollToTopClick = onScrollToTopClick
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = if (isShowing) 1 else 0

    @SuppressLint("NotifyDataSetChanged")
    fun show() {
        isShowing = true
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun hide() {
        isShowing = false
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun showOrHide(shouldBeShowing: Boolean) {
        isShowing = shouldBeShowing
        notifyDataSetChanged()
    }
}
