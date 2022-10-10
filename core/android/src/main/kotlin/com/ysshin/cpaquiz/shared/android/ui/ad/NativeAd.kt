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
import timber.log.Timber

@Composable
fun NativeMediumAd() {
    var isAdRequested = false

    AndroidViewBinding(factory = LayoutNativeAdMediumBinding::inflate) {
        if (isAdRequested) {
            Timber.d("NativeMediumAd is already requested.")
            return@AndroidViewBinding
        }

        Timber.d("Create LayoutNativeAdMediumBinding.")

        val adView = root.also { adView ->
            adView.advertiserView = tvAdvertiser
            adView.bodyView = tvBody
            adView.callToActionView = btnCta
            adView.headlineView = tvHeadline
            adView.iconView = ivAppIcon
            adView.priceView = tvPrice
            adView.starRatingView = rtbStars
            adView.storeView = tvStore
            adView.mediaView = mvContent
        }

        runCatching {
            AdLoader.Builder(adView.context, AdConstants.QUIZ_NATIVE_AD_MEDIUM)
                .forNativeAd { nativeAd ->
                    nativeAd.advertiser?.let { advertiser ->
                        tvAdvertiser.text = advertiser
                    }

                    nativeAd.body?.let { body ->
                        tvBody.text = body
                    }

                    nativeAd.callToAction?.let { cta ->
                        btnCta.text = cta
                    }

                    nativeAd.headline?.let { headline ->
                        tvHeadline.text = headline
                    }

                    nativeAd.icon?.let { icon ->
                        ivAppIcon.setImageDrawable(icon.drawable)
                    }

                    nativeAd.price?.let { price ->
                        tvPrice.text = price
                    }

                    nativeAd.starRating?.let { rating ->
                        rtbStars.rating = rating.toFloat()
                    }

                    nativeAd.store?.let { store ->
                        tvStore.text = store
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
        }.onSuccess {
            Timber.d("Load NativeMediumAd.")
            it.loadAd(AdRequest.Builder().build())
            isAdRequested = true
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NativeMediumAdPreview() {
    NativeMediumAd()
}

@Composable
fun NativeSmallAd() {
    var isAdRequested = false

    AndroidViewBinding(factory = LayoutNativeAdSmallBinding::inflate) {
        if (isAdRequested) {
            Timber.d("NativeSmallAd is already requested.")
            return@AndroidViewBinding
        }
        Timber.d("Create LayoutNativeAdSmallBinding.")

        val adView = root.also { adView ->
            adView.bodyView = tvBody
            adView.callToActionView = btnCta
            adView.headlineView = tvHeadline
            adView.iconView = ivAppIcon
            adView.storeView = tvStore
        }

        runCatching {
            AdLoader.Builder(adView.context, AdConstants.QUIZ_NATIVE_AD_SMALL)
                .forNativeAd { nativeAd ->
                    nativeAd.body?.let { body ->
                        tvBody.text = body
                    }

                    nativeAd.callToAction?.let { cta ->
                        btnCta.text = cta
                    }

                    nativeAd.headline?.let { headline ->
                        tvHeadline.text = headline
                    }

                    nativeAd.icon?.let { icon ->
                        ivAppIcon.setImageDrawable(icon.drawable)
                    }

                    nativeAd.store?.let { store ->
                        tvStore.text = store
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
        }.onSuccess {
            Timber.d("Load NativeSmallAd.")
            it.loadAd(AdRequest.Builder().build())
            isAdRequested = true
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NativeSmallAdPreview() {
    NativeMediumAd()
}
