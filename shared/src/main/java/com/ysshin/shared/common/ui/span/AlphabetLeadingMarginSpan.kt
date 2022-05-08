package com.ysshin.shared.common.ui.span

import android.graphics.Canvas
import android.graphics.Paint
import android.text.Layout
import android.text.style.LeadingMarginSpan

class AlphabetLeadingMarginSpan : LeadingMarginSpan {

    private var indentMargin: Int = 0

    override fun getLeadingMargin(first: Boolean): Int = if (first) 0 else indentMargin

    override fun drawLeadingMargin(
        canvas: Canvas,
        paint: Paint,
        x: Int,
        dir: Int,
        top: Int,
        baseline: Int,
        bottom: Int,
        text: CharSequence,
        start: Int,
        end: Int,
        first: Boolean,
        layout: Layout
    ) {
        if (first.not()) {
            return
        }

        val lineStartText = runCatching { text.substring(start, start + 2) }.getOrNull() ?: return

        indentMargin = if (ALPHABET_INDENT_REGEX.matches(lineStartText)) {
            paint.measureText("$lineStartText ").toInt()
        } else {
            0
        }
    }

    companion object {
        private val ALPHABET_INDENT_REGEX = "[A-Za-z].".toRegex()
    }
}