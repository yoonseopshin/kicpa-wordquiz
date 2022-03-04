package com.cpa.cpa_word_problem.feature.quiz.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cpa.cpa_word_problem.databinding.LayoutAdBannerBinding
import com.google.android.gms.ads.AdRequest

class AdBannerAdapter : RecyclerView.Adapter<AdBannerAdapter.AdBannerViewHolder>() {

    class AdBannerViewHolder(private val binding: LayoutAdBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            binding.adView.loadAd(AdRequest.Builder().build())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AdBannerViewHolder(
        LayoutAdBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: AdBannerViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = 1

}