package com.ysshin.cpaquiz.core.android.util

import com.ysshin.cpaquiz.core.android.R
import com.ysshin.cpaquiz.domain.model.QuizType

fun chipContainerColorResIdByType(type: QuizType) = when (type) {
    QuizType.Accounting -> R.color.accounting_highlight_color
    QuizType.Business -> R.color.business_highlight_color
    QuizType.CommercialLaw -> R.color.commercial_law_highlight_color
    QuizType.TaxLaw -> R.color.tax_law_highlight_color
}
