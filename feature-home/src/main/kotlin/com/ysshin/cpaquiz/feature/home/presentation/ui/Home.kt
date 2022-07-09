package com.ysshin.cpaquiz.feature.home.presentation.ui

import android.content.Context
import android.graphics.drawable.ColorDrawable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.LocalElevationOverlay
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.google.accompanist.flowlayout.SizeMode
import com.google.android.ads.nativetemplates.NativeTemplateStyle
import com.google.android.ads.nativetemplates.TemplateView
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.ysshin.cpaquiz.feature.home.R
import com.ysshin.cpaquiz.feature.home.presentation.screen.main.HomeViewModel
import com.ysshin.cpaquiz.shared.android.ui.theme.CpaQuizTheme
import com.ysshin.cpaquiz.shared.android.ui.theme.Typography
import com.ysshin.cpaquiz.shared.android.util.AdConstants
import com.ysshin.cpaquiz.shared.base.Action

@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel()) {
    CpaQuizTheme {
        val scaffoldState = rememberScaffoldState()
        val context = LocalContext.current

        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                CompositionLocalProvider(LocalElevationOverlay provides null) {
                    val dday by viewModel.dday.collectAsState()
                    TopAppBar(
                        title = {
                            Text(
                                text = if (dday.isBlank()) "" else stringResource(id = R.string.dday, dday),
                                modifier = Modifier.fillMaxWidth(),
                            )
                        },
                        backgroundColor = colorResource(id = R.color.theme_color)
                    )
                    // TODO: Menu with BottomSheet UI
                }
            }
        ) { padding ->
            FlowRow(
                modifier = Modifier
                    .padding(padding)
                    .padding(vertical = 28.dp),
                mainAxisAlignment = MainAxisAlignment.Center,
                mainAxisSize = SizeMode.Expand,
                crossAxisSpacing = 20.dp,
                mainAxisSpacing = 20.dp
            ) {
                val accountingCount by viewModel.accountingCount.collectAsState()
                QuizCard(
                    cardBackgroundColor = colorResource(id = R.color.accounting_highlight_color_0_20),
                    iconBackgroundColor = colorResource(id = R.color.accounting_highlight_color),
                    count = accountingCount,
                    title = stringResource(id = R.string.accounting)
                )

                val businessCount by viewModel.businessCount.collectAsState()
                QuizCard(
                    cardBackgroundColor = colorResource(id = R.color.business_highlight_color_0_20),
                    iconBackgroundColor = colorResource(id = R.color.business_highlight_color),
                    count = businessCount,
                    title = stringResource(id = R.string.business)
                )

                val commercialLawCount by viewModel.commercialLawCount.collectAsState()
                QuizCard(
                    cardBackgroundColor = colorResource(id = R.color.commercial_law_highlight_color_0_20),
                    iconBackgroundColor = colorResource(id = R.color.commercial_law_highlight_color),
                    count = commercialLawCount,
                    title = stringResource(id = R.string.commercial_law)
                )

                val taxLawCount by viewModel.taxLawCount.collectAsState()
                QuizCard(
                    cardBackgroundColor = colorResource(id = R.color.tax_law_highlight_color_0_20),
                    iconBackgroundColor = colorResource(id = R.color.tax_law_highlight_color),
                    count = taxLawCount,
                    title = stringResource(id = R.string.tax_law)
                )

                // TODO: NativeAd()
            }
        }
    }
}

@Composable
fun QuizCard(
    cardBackgroundColor: Color,
    iconBackgroundColor: Color,
    count: Int,
    title: String,
    onClick: Action = {},
) {
    val cornerShape = RoundedCornerShape(24.dp)

    Card(
        modifier = Modifier
            .clip(cornerShape)
            .clickable(onClick = onClick),
        shape = cornerShape,
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(cardBackgroundColor)
                .padding(horizontal = 16.dp, vertical = 32.dp),
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(40.dp),
                    elevation = null,
                    colors = ButtonDefaults.buttonColors(backgroundColor = iconBackgroundColor),
                    onClick = {},
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_play),
                        contentDescription = null
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Column(modifier = Modifier.defaultMinSize(minWidth = 64.dp)) {
                    Text(
                        text = stringResource(id = R.string.quiz_count, count),
                        style = Typography.caption,
                        color = colorResource(id = R.color.daynight_gray500s))
                    Text(
                        text = title,
                        style = Typography.subtitle1
                    )
                }
            }
        }
    }
}

@Composable
fun NativeAd() {
    AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = { context ->
            TemplateView(context).also { adView ->
                loadAd(context, adView)
            }
        }
    )
}

private fun loadAd(context: Context, adTemplateView: TemplateView) {
    runCatching {
        val adLoader = AdLoader.Builder(context, AdConstants.QUIZ_NATIVE_AD_MEDIUM)
            .forNativeAd { nativeAd ->
//                val styles = NativeTemplateStyle.Builder()
//                    .withMainBackgroundColor(ColorDrawable(context.getColor(R.color.theme_color)))
//                    .withCallToActionBackgroundColor(ColorDrawable(context.getColor(R.color.primaryDarkColor)))
//                    .withCallToActionTypefaceColor(context.getColor(R.color.secondaryTextColor))
//                    .build()
//                adTemplateView.setStyles(styles)
                adTemplateView.setNativeAd(nativeAd)
            }
            .withNativeAdOptions(NativeAdOptions.Builder().build())
            .build()
        adLoader
    }.onSuccess {
        it.loadAd(AdRequest.Builder().build())
    }
}

@Preview(showBackground = true)
@Composable
private fun QuizCardPreview() {
    QuizCard(
        cardBackgroundColor = colorResource(id = R.color.business_highlight_color_0_20),
        iconBackgroundColor = colorResource(id = R.color.business_highlight_color),
        count = 131,
        title = stringResource(id = R.string.business)
    )
}
