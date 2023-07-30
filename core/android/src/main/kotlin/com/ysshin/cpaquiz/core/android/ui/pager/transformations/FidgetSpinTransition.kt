package com.ysshin.cpaquiz.core.android.ui.pager.transformations

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import com.ysshin.cpaquiz.core.android.ui.pager.calculateCurrentOffsetForPage
import kotlin.math.absoluteValue
import kotlin.math.pow

@OptIn(ExperimentalFoundationApi::class)
fun Modifier.pagerFidgetSpinTransition(page: Int, pagerState: PagerState) = graphicsLayer {
    // Calculate the absolute offset for the current page from the
    // scroll position.
    val pageOffset = pagerState.calculateCurrentOffsetForPage(page)
    translationX = pageOffset * size.width

    if (pageOffset < -1f) {
        // page is far off screen
        alpha = 0f
    } else if (pageOffset <= 0) {
        // page is to the right of the selected page or the selected page
        alpha = 1f
        rotationZ = -36000f * pageOffset.absoluteValue.pow(7)
    } else if (pageOffset <= 1) {
        // page is to the left of the selected page
        alpha = 1f
        rotationZ = 36000f * pageOffset.absoluteValue.pow(7)
    } else {
        alpha = 0f
    }

    if (pageOffset.absoluteValue <= 0.5) {
        alpha = 1f
        scaleX = (1 - pageOffset.absoluteValue)
        scaleY = (1 - pageOffset.absoluteValue)
    } else if (pageOffset.absoluteValue > 0.5) {
        alpha = 0f
    }
}
