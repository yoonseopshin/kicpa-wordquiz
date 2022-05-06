package com.cpa.cpa_word_problem.feature.quiz.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cpa.cpa_word_problem.databinding.LayoutNoteResultHeaderBinding

class NoteResultHeaderAdapter : RecyclerView.Adapter<NoteResultHeaderAdapter.ItemViewHolder>() {

    var onNoteResultHeaderClick: (() -> Unit)? = null

    class ItemViewHolder(
        binding: LayoutNoteResultHeaderBinding,
        onNoteResultHeaderClick: (() -> Unit)? = null
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setOnClickListener {
                onNoteResultHeaderClick?.invoke()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ItemViewHolder(
        LayoutNoteResultHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        onNoteResultHeaderClick
    )

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) = Unit

    override fun getItemCount() = 1

}
