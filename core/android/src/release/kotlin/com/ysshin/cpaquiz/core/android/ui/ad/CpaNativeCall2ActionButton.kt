package com.ysshin.cpaquiz.core.android.ui.ad

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.RippleDrawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.ysshin.cpaquiz.core.android.R

class CpaNativeCall2ActionButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : AppCompatButton(context, attrs, defStyleAttr) {

    private var buttonTextColor: Int = currentTextColor
    private var buttonContainerColor: Int = if (background is ColorDrawable) {
        (background as ColorDrawable).color
    } else {
        Color.TRANSPARENT
    }

    init {
        setupBackground()
    }

    private fun setupBackground() {
        val radius = resources.getDimensionPixelSize(R.dimen.button_corner_radius).toFloat()

        // Create a shape drawable with rounded corners
        val shapeDrawable = GradientDrawable().apply {
            cornerRadius = radius
            setColor(buttonContainerColor)
        }

        // Create a ColorStateList for the ripple effect
        val rippleColorStateList = ColorStateList.valueOf(
            ContextCompat.getColor(context, R.color.button_ripple_color),
        )

        // Use LayerDrawable to add a ripple effect
        val rippleDrawable = RippleDrawable(rippleColorStateList, shapeDrawable, null)
        val layers = arrayOf(rippleDrawable, shapeDrawable)

        // Combine the layers and set as the background
        val layerDrawable = LayerDrawable(layers)
        background = layerDrawable

        setTextColor(buttonTextColor)
    }

    fun updateColors(textColor: Int, containerColor: Int) {
        buttonTextColor = textColor
        buttonContainerColor = containerColor
        setupBackground()
    }
}
