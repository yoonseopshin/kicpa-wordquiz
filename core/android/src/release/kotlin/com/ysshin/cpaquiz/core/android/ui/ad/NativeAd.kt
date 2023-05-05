package com.ysshin.cpaquiz.core.android.ui.ad

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.ysshin.cpaquiz.core.android.R
import com.ysshin.cpaquiz.core.android.databinding.LayoutNativeAdMediumBinding
import com.ysshin.cpaquiz.core.android.databinding.LayoutNativeAdSmallBinding
import com.ysshin.cpaquiz.core.android.util.AdConstants
import com.ysshin.cpaquiz.core.android.util.visible
import com.ysshin.cpaquiz.designsystem.component.CpaLoadingWheel
import com.ysshin.cpaquiz.designsystem.icon.CpaIcon
import com.ysshin.cpaquiz.designsystem.icon.CpaIcons

private enum class AdState {
    Loading, Success, Error;
}

@Composable
private fun BoxScope.NativeMediumAdError() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.align(Alignment.Center),
    ) {
        CpaIcon(
            icon = CpaIcons.Warning,
            modifier = Modifier.size(56.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.ad_load_failed),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun NativeMediumAd() {
    var adState by rememberSaveable {
        mutableStateOf(AdState.Loading)
    }

    Box(modifier = Modifier.defaultMinSize(minHeight = 300.dp)) {
        when (adState) {
            AdState.Loading -> CpaLoadingWheel(
                contentDesc = "Loading",
                modifier = Modifier.align(Alignment.Center)
            )

            AdState.Error -> NativeMediumAdError()
            AdState.Success -> {}
        }

        AndroidViewBinding(
            factory = { inflater, parent, attachToParent ->
                adState = AdState.Loading

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

                try {
                    val adLoader = AdLoader.Builder(
                        adView.context,
                        AdConstants.getNativeMediumAdUnitId()
                    )
                        .forNativeAd { nativeAd ->
                            nativeAd.advertiser?.let {
                                if (it.isNotBlank()) {
                                    binding.tvAdvertiser.text = it
                                    binding.tvAdvertiser.visible()
                                }
                            }
                            nativeAd.body?.let {
                                if (it.isNotBlank()) {
                                    binding.tvBody.text = it
                                    binding.tvBody.visible()
                                }
                            }
                            nativeAd.callToAction?.let {
                                if (it.isNotBlank()) {
                                    binding.btnCta.text = it
                                    binding.btnCta.visible()
                                }
                            }
                            nativeAd.icon?.let {
                                binding.ivAppIcon.setImageDrawable(it.drawable)
                                binding.ivAppIcon.visible()
                            }
                            nativeAd.headline?.let {
                                if (it.isNotBlank()) {
                                    binding.tvHeadline.text = it
                                    binding.tvHeadline.visible()
                                }
                            }
                            nativeAd.price?.let {
                                if (it.isNotBlank()) {
                                    binding.tvPrice.text = it
                                    binding.tvPrice.visible()
                                }
                            }
                            nativeAd.starRating?.let {
                                binding.rtbStars.rating = it.toFloat()
                                binding.rtbStars.visible()
                            }
                            nativeAd.store?.let {
                                if (it.isNotBlank()) {
                                    binding.tvStore.text = it
                                    binding.tvStore.visible()
                                }
                            }
                            adView.setNativeAd(nativeAd)
                        }
                        .withAdListener(object : AdListener() {
                            override fun onAdFailedToLoad(error: LoadAdError) {
                                super.onAdFailedToLoad(error)
                                adState = AdState.Error
                            }

                            override fun onAdLoaded() {
                                super.onAdLoaded()
                                adState = AdState.Success
                                adView.visible(withAnimation = true)
                            }
                        })
                        .withNativeAdOptions(NativeAdOptions.Builder().build())
                        .build()
                    adLoader.loadAd(AdRequest.Builder().build())
                } catch (ignore: Exception) {
                }

                binding
            },
            update = {
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NativeMediumAdPreview() {
    NativeMediumAd()
}

@Composable
private fun BoxScope.NativeSmallAdError() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.align(Alignment.Center),
    ) {
        CpaIcon(
            icon = CpaIcons.Warning,
            modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = stringResource(id = R.string.ad_load_failed),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun NativeSmallAd() {
    var adState by rememberSaveable {
        mutableStateOf(AdState.Loading)
    }

    Box(modifier = Modifier.defaultMinSize(minHeight = 64.dp)) {
        when (adState) {
            AdState.Loading -> CpaLoadingWheel(
                contentDesc = "Loading",
                modifier = Modifier.align(Alignment.Center)
            )

            AdState.Error -> NativeSmallAdError()
            AdState.Success -> {}
        }

        AndroidViewBinding(factory = { inflater, parent, attachToParent ->
            adState = AdState.Loading

            val binding = LayoutNativeAdSmallBinding.inflate(inflater, parent, attachToParent)
            val adView = binding.root.also { adView ->
                adView.bodyView = binding.tvBody
                adView.callToActionView = binding.btnCta
                adView.headlineView = binding.tvHeadline
                adView.iconView = binding.ivAppIcon
                adView.storeView = binding.tvStore
            }

            try {
                val adLoader = AdLoader.Builder(adView.context, AdConstants.getNativeSmallAdUnitId())
                    .forNativeAd { nativeAd ->
                        nativeAd.icon?.let {
                            binding.ivAppIcon.setImageDrawable(it.drawable)
                            binding.ivAppIcon.visible()
                        }
                        nativeAd.headline?.let {
                            binding.tvHeadline.text = it
                            binding.tvHeadline.visible()
                        }
                        nativeAd.body?.let {
                            binding.tvBody.text = it
                            binding.tvBody.visible()
                        }
                        nativeAd.callToAction?.let { binding.btnCta.text = it }
                        nativeAd.store?.let {
                            binding.tvStore.text = it
                            binding.tvStore.visible()
                        }
                        adView.setNativeAd(nativeAd)
                    }
                    .withAdListener(object : AdListener() {
                        override fun onAdFailedToLoad(error: LoadAdError) {
                            super.onAdFailedToLoad(error)
                            adState = AdState.Error
                        }

                        override fun onAdLoaded() {
                            super.onAdLoaded()
                            adState = AdState.Success
                            adView.visible(withAnimation = true)
                        }
                    })
                    .withNativeAdOptions(NativeAdOptions.Builder().build())
                    .build()
                adLoader.loadAd(AdRequest.Builder().build())
            } catch (ignore: Exception) {
            }

            binding
        })
    }
}

@Preview(showBackground = true)
@Composable
private fun NativeSmallAdPreview() {
    NativeSmallAd()
}
