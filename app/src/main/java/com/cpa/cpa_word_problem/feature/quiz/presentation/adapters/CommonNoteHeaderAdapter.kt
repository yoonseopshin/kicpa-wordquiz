package com.cpa.cpa_word_problem.feature.quiz.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cpa.cpa_word_problem.databinding.LayoutCommonNoteHeaderBinding

class CommonNoteHeaderAdapter : RecyclerView.Adapter<CommonNoteHeaderAdapter.ItemViewHolder>() {

    var headerTitle: String = ""
    var isShowing: Boolean = true
        set(value) {
            notifyDataSetChanged()
            field = value
        }
    var onHeaderLongClick: (() -> Unit)? = null

    class ItemViewHolder(
        private val binding: LayoutCommonNoteHeaderBinding,
        onHeaderLongClick: (() -> Unit)? = null,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setOnLongClickListener {
                onHeaderLongClick?.invoke()
                true
            }
        }

        fun bind(title: String) {
            binding.headerTitle = title
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ItemViewHolder(
        LayoutCommonNoteHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        onHeaderLongClick
    )

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(headerTitle)
    }

    override fun getItemCount() = if (isShowing) 1 else 0

}

fun CommonNoteHeaderAdapter.show() {
    isShowing = true
    notifyDataSetChanged()
}

fun CommonNoteHeaderAdapter.hide() {
    isShowing = false
    notifyDataSetChanged()
}

fun CommonNoteHeaderAdapter.showOrHide(shouldBeShowing: Boolean) {
    isShowing = shouldBeShowing
    notifyDataSetChanged()
}
