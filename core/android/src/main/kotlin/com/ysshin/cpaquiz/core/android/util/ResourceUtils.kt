package com.ysshin.cpaquiz.core.android.util

import com.ysshin.cpaquiz.core.android.R
import com.ysshin.cpaquiz.domain.model.QuizType

fun chipContainerColorResIdByType(type: QuizType) = when (type) {
    QuizType.Accounting -> R.color.accounting_highlight_color_0_20
    QuizType.Business -> R.color.business_highlight_color_0_20
    QuizType.CommercialLaw -> R.color.commercial_law_highlight_color_0_20
    QuizType.TaxLaw -> R.color.tax_law_highlight_color_0_20
}

fun chipBorderColorResIdByType(type: QuizType) = when (type) {
    QuizType.Accounting -> R.color.accounting_highlight_color
    QuizType.Business -> R.color.business_highlight_color
    QuizType.CommercialLaw -> R.color.commercial_law_highlight_color
    QuizType.TaxLaw -> R.color.tax_law_highlight_color
}

// TODO: Move to designsystem module
fun filterChipBackgroundColorResourceIdByFiltering(isFiltering: Boolean) = if (isFiltering) {
    R.color.primaryColor_0_15
} else {
    R.color.daynight_gray070s
}

fun filterChipStrokeColorResourceIdByFiltering(isFiltering: Boolean) = if (isFiltering) {
    R.color.primaryColor
} else {
    R.color.daynight_gray300s
}
