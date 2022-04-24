package com.cpa.cpa_word_problem.feature.quiz.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cpa.cpa_word_problem.databinding.LayoutCommonNoteHeaderBinding

class CommonNoteHeaderAdapter : RecyclerView.Adapter<CommonNoteHeaderAdapter.ItemViewHolder>() {

    var headerTitle = ""
    var isShowing = true
    var isToggleable = false
    var isOpened = true
    var onHeaderClick: (() -> Unit)? = null
    var onHeaderLongClick: (() -> Unit)? = null

    class ItemViewHolder(
        private val binding: LayoutCommonNoteHeaderBinding,
        onHeaderClick: (() -> Unit)? = null,
        onHeaderLongClick: (() -> Unit)? = null,
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setOnClickListener {
                onHeaderClick?.invoke()
                if (binding.isToggleable) {
                    binding.isOpened = binding.isOpened.not()
                    binding.executePendingBindings()
                }
            }

            binding.setOnLongClickListener {
                onHeaderLongClick?.invoke()
                true
            }
        }

        fun bind(title: String, isToggleable: Boolean, isOpened: Boolean) {
            binding.headerTitle = title
            binding.isToggleable = isToggleable
            binding.isOpened = isOpened
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ItemViewHolder(
        LayoutCommonNoteHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        onHeaderClick,
        onHeaderLongClick
    )

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(headerTitle, isToggleable, isOpened)
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
