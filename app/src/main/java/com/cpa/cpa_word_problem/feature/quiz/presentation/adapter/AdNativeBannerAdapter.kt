package com.cpa.cpa_word_problem.feature.quiz.presentation.adapter

import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cpa.cpa_word_problem.R
import com.cpa.cpa_word_problem.databinding.LayoutAdNativeBannerBinding
import com.cpa.cpa_word_problem.feature.quiz.presentation.util.AdConstants
import com.ysshin.cpaquiz.shared.android.util.color
import com.google.android.ads.nativetemplates.NativeTemplateStyle
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.ysshin.cpaquiz.shared.android.util.inflate

class AdNativeBannerAdapter : RecyclerView.Adapter<AdNativeBannerAdapter.AdBannerViewHolder>() {

    class AdBannerViewHolder(private val binding: LayoutAdNativeBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val adLoader: AdLoader
        private var adLoaded = false

        init {
            val context = binding.root.context
            adLoader = AdLoader.Builder(context, AdConstants.QUIZ_NATIVE_AD_SMALL)
                .forNativeAd { nativeAd ->
                    val styles = NativeTemplateStyle.Builder()
                        .withMainBackgroundColor(ColorDrawable(context.color(R.color.theme_color)))
                        .withCallToActionBackgroundColor(ColorDrawable(context.color(R.color.primaryDarkColor)))
                        .withCallToActionTypefaceColor(context.color(R.color.secondaryTextColor))
                        .build()
                    binding.adTemplateView.setStyles(styles)
                    binding.adTemplateView.setNativeAd(nativeAd)
                }
                .withAdListener(object : AdListener() {
                    override fun onAdLoaded() {
                        super.onAdLoaded()
                        adLoaded = true
                    }
                })
                .withNativeAdOptions(NativeAdOptions.Builder().build())
                .build()
        }

        fun bind() {
            if (adLoaded.not()) {
                adLoader.loadAd(AdRequest.Builder().build())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AdBannerViewHolder(
        parent.inflate(LayoutAdNativeBannerBinding::inflate)
    )

    override fun onBindViewHolder(holder: AdBannerViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = 1

}