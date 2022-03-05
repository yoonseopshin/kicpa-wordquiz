package com.cpa.cpa_word_problem.feature.quiz.presentation.adapters

import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cpa.cpa_word_problem.R
import com.cpa.cpa_word_problem.databinding.LayoutAdNativeBannerBinding
import com.cpa.cpa_word_problem.feature.quiz.presentation.util.AdConstants
import com.cpa.cpa_word_problem.utils.colorAsInt
import com.google.android.ads.nativetemplates.NativeTemplateStyle
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAdOptions

class AdNativeBannerAdapter : RecyclerView.Adapter<AdNativeBannerAdapter.AdBannerViewHolder>() {

    class AdBannerViewHolder(private val binding: LayoutAdNativeBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var adLoader: AdLoader

        init {
            val context = binding.root.context
            adLoader = AdLoader.Builder(context, AdConstants.QUIZ_NATIVE_AD_SMALL)
                .forNativeAd { nativeAd ->
                    val styles = NativeTemplateStyle.Builder()
                        .withMainBackgroundColor(ColorDrawable(context.colorAsInt(R.color.theme_color)))
                        .withCallToActionBackgroundColor(ColorDrawable(context.colorAsInt(R.color.primaryDarkColor)))
                        .withCallToActionTypefaceColor(context.colorAsInt(R.color.secondaryTextColor))
                        .build()
                    binding.adTemplateView.setStyles(styles)
                    binding.adTemplateView.setNativeAd(nativeAd)


                    if (adLoader.isLoading) {

                    } else {

                    }
//
//                    if (isDetached) {
//                        nativeAd.destroy()
//                        return@forNativeAd
//                    }
                }
                .withAdListener(object : AdListener() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        // TODO: Handle AdError
                    }

                    override fun onAdClicked() {
                        super.onAdClicked()
                    }

                    override fun onAdClosed() {
                        super.onAdClosed()
                    }

                    override fun onAdImpression() {
                        super.onAdImpression()
                    }

                    override fun onAdLoaded() {
                        super.onAdLoaded()
                    }

                    override fun onAdOpened() {
                        super.onAdOpened()
                    }
                })
                .withNativeAdOptions(NativeAdOptions.Builder().build())
                .build()
        }

        fun bind() {
            adLoader.loadAd(AdRequest.Builder().build())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AdBannerViewHolder(
        LayoutAdNativeBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: AdBannerViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = 1

}