package com.cpa.cpa_word_problem.feature.quiz.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cpa.cpa_word_problem.databinding.LayoutNoteResultHeaderBinding
import com.ysshin.shared.util.Action

class NoteResultHeaderAdapter : RecyclerView.Adapter<NoteResultHeaderAdapter.ItemViewHolder>() {

    var onNoteResultHeaderClick: Action = {}

    class ItemViewHolder(binding: LayoutNoteResultHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var onNoteResultHeaderClick: Action = {}

        init {
            binding.setOnClickListener {
                onNoteResultHeaderClick()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ItemViewHolder(
        LayoutNoteResultHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    ).also { viewHolder ->
        viewHolder.onNoteResultHeaderClick = onNoteResultHeaderClick
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) = Unit

    override fun getItemCount() = 1

}
