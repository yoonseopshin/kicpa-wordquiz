package com.cpa.cpa_word_problem.feature.quiz.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cpa.cpa_word_problem.databinding.LayoutCommonNoteHeaderBinding
import com.ysshin.shared.common.ui.adapter.ToggleableAdapter
import com.ysshin.shared.util.Action
import com.ysshin.shared.util.Supplier

class CommonNoteHeaderAdapter : ToggleableAdapter<CommonNoteHeaderAdapter.ItemViewHolder>() {

    var headerTitle = ""

    var onHeaderClick: Action = {}
    var onHeaderLongClick: Action = {}

    class ItemViewHolder(private val binding: LayoutCommonNoteHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var onHeaderClick: Action = {}
        var onHeaderLongClick: Action = {}

        init {
            binding.setOnClickListener {
                onHeaderClick()
                if (binding.isToggleable) {
                    binding.isShowing = binding.isShowing.not()
                    binding.executePendingBindings()
                }
            }

            binding.setOnLongClickListener {
                onHeaderLongClick()
                true
            }
        }

        fun bind(title: String, isToggleable: Boolean, isShowing: Boolean) {
            binding.headerTitle = title
            binding.isToggleable = isToggleable
            binding.isShowing = isShowing
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ItemViewHolder(
        LayoutCommonNoteHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    ).also { viewHolder ->
        viewHolder.onHeaderClick = onHeaderClick
        viewHolder.onHeaderLongClick = onHeaderLongClick
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(headerTitle, isToggleable, isShowing)
    }

    override var itemCountSupplier: Supplier<Int>
        get() = { 1 }
        set(value) {}

}
