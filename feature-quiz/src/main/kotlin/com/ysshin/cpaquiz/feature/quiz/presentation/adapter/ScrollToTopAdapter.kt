package com.ysshin.cpaquiz.feature.quiz.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ysshin.cpaquiz.feature.quiz.databinding.LayoutScrollToTopBinding
import com.ysshin.cpaquiz.shared.android.ui.adapter.ToggleableAdapter
import com.ysshin.cpaquiz.shared.android.util.inflate
import com.ysshin.cpaquiz.shared.base.Action
import com.ysshin.cpaquiz.shared.base.Supplier

class ScrollToTopAdapter : ToggleableAdapter<ScrollToTopAdapter.ItemViewHolder>() {

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

    override var itemCountSupplier: Supplier<Int>
        get() = { 1 }
        set(value) = Unit

}
