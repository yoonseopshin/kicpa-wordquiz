package com.ysshin.cpaquiz.feature.quiz.presentation.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ysshin.cpaquiz.core.android.util.inflate
import com.ysshin.cpaquiz.core.android.util.visibleOrGone
import com.ysshin.cpaquiz.core.base.Action
import com.ysshin.cpaquiz.feature.quiz.databinding.LayoutCommonNoteHeaderBinding

class CommonNoteHeaderAdapter(val headerTitle: String) :
    RecyclerView.Adapter<CommonNoteHeaderAdapter.ItemViewHolder>() {

    var isShowing = true
    var numOfProblems = 0
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var onHeaderClick: Action = {}
    var onHeaderLongClick: Action = {}

    class ItemViewHolder(private val binding: LayoutCommonNoteHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var onHeaderClick: Action = {}
        var onHeaderLongClick: Action = {}

        init {
            binding.layoutHeader.setOnClickListener {
                onHeaderClick()
            }

            binding.layoutHeader.setOnLongClickListener {
                onHeaderLongClick()
                true
            }
        }

        fun bind(title: String, numOfProblems: Int) {
            binding.tvNoteHeader.text = title
            binding.tvNumOfProblems.text = numOfProblems.toString()
            binding.tvNumOfProblems.visibleOrGone(isVisible = numOfProblems > 0, withAnimation = true)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ItemViewHolder(
        parent.inflate(LayoutCommonNoteHeaderBinding::inflate)
    ).also { viewHolder ->
        viewHolder.onHeaderClick = onHeaderClick
        viewHolder.onHeaderLongClick = onHeaderLongClick
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(headerTitle, numOfProblems)
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
