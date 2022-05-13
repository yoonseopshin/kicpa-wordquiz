package com.cpa.cpa_word_problem.feature.quiz.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cpa.cpa_word_problem.databinding.LayoutAdSmartBannerBinding
import com.google.android.gms.ads.AdRequest
import com.ysshin.cpaquiz.shared.android.util.inflate

class AdSmartBannerAdapter : RecyclerView.Adapter<AdSmartBannerAdapter.AdBannerViewHolder>() {

    class AdBannerViewHolder(private val binding: LayoutAdSmartBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            binding.adView.loadAd(AdRequest.Builder().build())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AdBannerViewHolder(
        parent.inflate(LayoutAdSmartBannerBinding::inflate)
    )

    override fun onBindViewHolder(holder: AdBannerViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = 1

}