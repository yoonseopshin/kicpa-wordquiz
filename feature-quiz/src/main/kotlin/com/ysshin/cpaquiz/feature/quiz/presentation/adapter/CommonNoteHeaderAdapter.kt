package com.ysshin.cpaquiz.feature.quiz.presentation.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ysshin.cpaquiz.feature.quiz.databinding.LayoutCommonNoteHeaderBinding
import com.ysshin.cpaquiz.shared.android.util.inflate
import com.ysshin.cpaquiz.shared.base.Action

class CommonNoteHeaderAdapter : RecyclerView.Adapter<CommonNoteHeaderAdapter.ItemViewHolder>() {

    var headerTitle = ""

    var isShowing = true
    var onHeaderClick: Action = {}
    var onHeaderLongClick: Action = {}

    class ItemViewHolder(private val binding: LayoutCommonNoteHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var onHeaderClick: Action = {}
        var onHeaderLongClick: Action = {}

        init {
            binding.setOnClickListener {
                onHeaderClick()
            }

            binding.setOnLongClickListener {
                onHeaderLongClick()
                true
            }
        }

        fun bind(title: String, isShowing: Boolean) {
            binding.headerTitle = title
            binding.isShowing = isShowing
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ItemViewHolder(
        parent.inflate(LayoutCommonNoteHeaderBinding::inflate)
    ).also { viewHolder ->
        viewHolder.onHeaderClick = onHeaderClick
        viewHolder.onHeaderLongClick = onHeaderLongClick
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(headerTitle, isShowing)
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
