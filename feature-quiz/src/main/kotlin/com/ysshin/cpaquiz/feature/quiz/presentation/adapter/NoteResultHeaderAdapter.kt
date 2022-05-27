package com.ysshin.cpaquiz.feature.quiz.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ysshin.cpaquiz.feature.quiz.databinding.LayoutNoteResultHeaderBinding
import com.ysshin.cpaquiz.shared.android.util.inflate
import com.ysshin.cpaquiz.shared.base.Action

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
            parent.inflate(LayoutNoteResultHeaderBinding::inflate)
    ).also { viewHolder ->
        viewHolder.onNoteResultHeaderClick = onNoteResultHeaderClick
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) = Unit

    override fun getItemCount() = 1

}
