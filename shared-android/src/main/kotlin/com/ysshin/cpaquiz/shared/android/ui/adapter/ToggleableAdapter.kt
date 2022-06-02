package com.ysshin.cpaquiz.shared.android.ui.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.ysshin.cpaquiz.shared.base.Supplier

abstract class ToggleableAdapter<VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {

    var isShowing = true
    var isToggleable = false

    abstract var itemCountSupplier: Supplier<Int>

    override fun getItemCount() = if (isShowing) itemCountSupplier() else 0

    @SuppressLint("NotifyDataSetChanged")
    open fun show() {
        isShowing = true
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    open fun hide() {
        isShowing = false
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    open fun showOrHide(shouldBeShowing: Boolean) {
        isShowing = shouldBeShowing
        notifyDataSetChanged()
    }
}
