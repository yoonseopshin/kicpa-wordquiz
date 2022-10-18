package com.ysshin.cpaquiz.feature.quiz.presentation.adapter

import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.ads.nativetemplates.NativeTemplateStyle
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.ysshin.cpaquiz.core.android.util.AdConstants
import com.ysshin.cpaquiz.core.android.util.color
import com.ysshin.cpaquiz.core.android.util.inflate
import com.ysshin.cpaquiz.feature.quiz.R
import com.ysshin.cpaquiz.feature.quiz.databinding.LayoutAdNativeBannerBinding

class AdNativeBannerAdapter : RecyclerView.Adapter<AdNativeBannerAdapter.AdBannerViewHolder>() {

    class AdBannerViewHolder(private val binding: LayoutAdNativeBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var adRequested = false
        private val adLoader: AdLoader by lazy {
            val context = binding.root.context
            AdLoader.Builder(context, AdConstants.QUIZ_NATIVE_AD_SMALL)
                .forNativeAd { nativeAd ->
                    val styles = NativeTemplateStyle.Builder()
                        .withMainBackgroundColor(
                            ColorDrawable(context.color(R.color.theme_color))
                        )
                        .withCallToActionBackgroundColor(
                            ColorDrawable(context.color(R.color.primaryDarkColor))
                        )
                        .withCallToActionTypefaceColor(
                            context.color(R.color.secondaryTextColor)
                        )
                        .build()
                    binding.adTemplateView.setStyles(styles)
                    binding.adTemplateView.setNativeAd(nativeAd)
                }
                .withNativeAdOptions(NativeAdOptions.Builder().build())
                .build()
        }

        fun bind() {
            // Should be called only once in lifecycle
            if (adRequested.not()) {
                adRequested = true
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
