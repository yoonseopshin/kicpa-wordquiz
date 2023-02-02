package com.ysshin.cpaquiz.core.android.ui.ad

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.ysshin.cpaquiz.core.android.databinding.LayoutNativeAdMediumBinding
import com.ysshin.cpaquiz.core.android.databinding.LayoutNativeAdSmallBinding
import com.ysshin.cpaquiz.core.android.util.AdConstants
import com.ysshin.cpaquiz.core.android.util.visible

@Composable
fun NativeMediumAd() {
    AndroidViewBinding(
        factory = { inflater, parent, attachToParent ->
            val binding = LayoutNativeAdMediumBinding.inflate(inflater, parent, attachToParent)

            val adView = binding.root.also { adView ->
                adView.advertiserView = binding.tvAdvertiser
                adView.bodyView = binding.tvBody
                adView.callToActionView = binding.btnCta
                adView.headlineView = binding.tvHeadline
                adView.iconView = binding.ivAppIcon
                adView.priceView = binding.tvPrice
                adView.starRatingView = binding.rtbStars
                adView.storeView = binding.tvStore
                adView.mediaView = binding.mvContent
            }

            val adLoader = AdLoader.Builder(adView.context, AdConstants.QUIZ_NATIVE_AD_MEDIUM)
                .forNativeAd { nativeAd ->
                    nativeAd.advertiser?.let { advertiser ->
                        binding.tvAdvertiser.text = advertiser
                    }

                    nativeAd.body?.let { body ->
                        binding.tvBody.text = body
                    }

                    nativeAd.callToAction?.let { cta ->
                        binding.btnCta.text = cta
                    }

                    nativeAd.headline?.let { headline ->
                        binding.tvHeadline.text = headline
                    }

                    nativeAd.icon?.let { icon ->
                        binding.ivAppIcon.setImageDrawable(icon.drawable)
                    }

                    nativeAd.price?.let { price ->
                        binding.tvPrice.text = price
                    }

                    nativeAd.starRating?.let { rating ->
                        binding.rtbStars.rating = rating.toFloat()
                    }

                    nativeAd.store?.let { store ->
                        binding.tvStore.text = store
                    }

                    adView.setNativeAd(nativeAd)
                }
                .withAdListener(object : AdListener() {
                    override fun onAdLoaded() {
                        super.onAdLoaded()
                        adView.visible(withAnimation = true)
                    }
                })
                .withNativeAdOptions(NativeAdOptions.Builder().build())
                .build()
            adLoader.loadAd(AdRequest.Builder().build())

            binding
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun NativeMediumAdPreview() {
    NativeMediumAd()
}

@Composable
fun NativeSmallAd() {
    AndroidViewBinding(factory = { inflater, parent, attachToParent ->
        val binding = LayoutNativeAdSmallBinding.inflate(inflater, parent, attachToParent)

        val adView = binding.root.also { adView ->
            adView.bodyView = binding.tvBody
            adView.callToActionView = binding.btnCta
            adView.headlineView = binding.tvHeadline
            adView.iconView = binding.ivAppIcon
            adView.storeView = binding.tvStore
        }

        val adLoader = AdLoader.Builder(adView.context, AdConstants.QUIZ_NATIVE_AD_SMALL)
            .forNativeAd { nativeAd ->
                nativeAd.body?.let { binding.tvBody.text = it }
                nativeAd.callToAction?.let { binding.btnCta.text = it }
                nativeAd.headline?.let { binding.tvHeadline.text = it }
                nativeAd.icon?.let { binding.ivAppIcon.setImageDrawable(it.drawable) }
                nativeAd.store?.let { binding.tvStore.text = it }
                adView.setNativeAd(nativeAd)
            }
            .withAdListener(object : AdListener() {
                override fun onAdLoaded() {
                    super.onAdLoaded()
                    adView.visible(withAnimation = true)
                }
            })
            .withNativeAdOptions(NativeAdOptions.Builder().build())
            .build()
        adLoader.loadAd(AdRequest.Builder().build())

        binding
    })
}

@Preview(showBackground = true)
@Composable
private fun NativeSmallAdPreview() {
    NativeSmallAd()
}
