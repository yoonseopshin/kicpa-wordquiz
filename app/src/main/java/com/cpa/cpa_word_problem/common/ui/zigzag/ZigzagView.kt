package com.cpa.cpa_word_problem.common.ui.zigzag

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.cpa.cpa_word_problem.R
import timber.log.Timber

class ZigzagView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttrs: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttrs) {

    private var zigzagHeight = 0f
    private var zigzagElevation = 0f
    private var zigzagPaddingContent = 0f
    private var zigzagPaddingContentLeft = 0f
    private var zigzagPaddingContentRight = 0f
    private var zigzagPaddingContentTop = 0f
    private var zigzagPaddingContentBottom = 0f
    private var zigzagBackgroundColor = ContextCompat.getColor(context, R.color.white)
        set(@ColorInt value) {
            field = value
            paintZigzag.color = value
            invalidate()
        }
    private var zigzagPadding = 0f
    private var zigzagPaddingLeft = 0f
    private var zigzagPaddingRight = 0f
    private var zigzagPaddingTop = 0f
    private var zigzagPaddingBottom = 0f
    private var zigzagSides = 0

    private val pathZigzag = Path()
    private val paintZigzag by lazy {
        Paint().apply {
            color = ContextCompat.getColor(context, R.color.black)
            style = Paint.Style.FILL
        }
    }

    private var rectMain = Rect()
    private var rectZigzag = RectF()
    private var rectContent = RectF()

    init {
        context.obtainStyledAttributes(attrs, R.styleable.ZigzagView).run {
            try {
                zigzagHeight = getDimension(R.styleable.ZigzagView_zigzagHeight, 0.0f)
                zigzagPaddingContent =
                    getDimension(R.styleable.ZigzagView_zigzagPaddingContent, 0.0f)
                zigzagPaddingContentLeft =
                    getDimension(
                        R.styleable.ZigzagView_zigzagPaddingContentLeft,
                        zigzagPaddingContent
                    )
                zigzagPaddingContentRight =
                    getDimension(
                        R.styleable.ZigzagView_zigzagPaddingContentRight,
                        zigzagPaddingContent
                    )
                zigzagPaddingContentTop =
                    getDimension(
                        R.styleable.ZigzagView_zigzagPaddingContentTop,
                        zigzagPaddingContent
                    )
                zigzagPaddingContentBottom =
                    getDimension(
                        R.styleable.ZigzagView_zigzagPaddingContentBottom,
                        zigzagPaddingContent
                    )
                zigzagBackgroundColor =
                    getColor(R.styleable.ZigzagView_zigzagBackgroundColor, zigzagBackgroundColor)
                zigzagPadding = getDimension(R.styleable.ZigzagView_zigzagPadding, zigzagElevation)
                zigzagPaddingLeft =
                    getDimension(R.styleable.ZigzagView_zigzagPaddingLeft, zigzagPadding)
                zigzagPaddingRight =
                    getDimension(R.styleable.ZigzagView_zigzagPaddingRight, zigzagPadding)
                zigzagPaddingTop =
                    getDimension(R.styleable.ZigzagView_zigzagPaddingTop, zigzagPadding)
                zigzagPaddingBottom =
                    getDimension(R.styleable.ZigzagView_zigzagPaddingBottom, zigzagPadding)
                zigzagSides = getInt(R.styleable.ZigzagView_zigzagSides, ZIGZAG_BOTTOM)
            } catch (e: Exception) {
                Timber.e(e)
            } finally {
                recycle()
            }
        }

        setLayerType(LAYER_TYPE_SOFTWARE, null)
        setWillNotDraw(false)
    }

    /**
     * 뷰의 기하, 좌표계를 설정하는 단계
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        rectMain.set(0, 0, measuredWidth, measuredHeight)

        // 입력받은 패딩 포함하여 지그재그 뷰 크기 계산
        rectZigzag.set(
            rectMain.left + zigzagPaddingLeft,
            rectMain.top + zigzagPaddingTop,
            rectMain.right - zigzagPaddingRight,
            rectMain.bottom - zigzagPaddingBottom
        )

        // 지그재그 뷰의 패딩만큼 영역을 조정
        rectContent.set(
            rectZigzag.left + zigzagPaddingContentLeft + (if (zigzagSides.containsSide(ZIGZAG_LEFT)) zigzagHeight else 0f),
            rectZigzag.top + zigzagPaddingContentTop + (if (zigzagSides.containsSide(ZIGZAG_TOP)) zigzagHeight else 0f),
            rectZigzag.right - zigzagPaddingContentRight + (if (zigzagSides.containsSide(
                    ZIGZAG_RIGHT
                )
            ) zigzagHeight else 0f),
            rectZigzag.bottom - zigzagPaddingContentBottom + (if (zigzagSides.containsSide(
                    ZIGZAG_BOTTOM
                )
            ) zigzagHeight else 0f),
        )

        // 상하좌우 패딩 설정
        setPadding(
            rectContent.left.toInt(),
            rectContent.top.toInt(),
            (rectMain.right - rectContent.right).toInt(),
            (rectMain.bottom - rectContent.bottom).toInt()
        )
    }

    /**
     * 뷰의 좌표계가 정해진 단계로, 좌표계를 바탕으로 뷰의 내용물을 배치하는 단계
     */
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    /**
     * 뷰의 좌표계가 정해진 단계로, 좌표계를 바탕으로 뷰를 그리는 단계
     */
    override fun onDraw(canvas: Canvas) {
        drawZigzag()
        canvas.drawPath(pathZigzag, paintZigzag)
    }

    private fun drawZigzag() {
        val left = rectZigzag.left
        val right = rectZigzag.right
        val top = rectZigzag.top
        val bottom = rectZigzag.bottom

        pathZigzag.moveTo(right, bottom)

        if (zigzagSides.containsSide(ZIGZAG_RIGHT) && zigzagHeight > 0) {
            drawVerticalSide(pathZigzag, top, right, bottom, isLeft = false)
        } else {
            pathZigzag.lineTo(right, top)
        }

        if (zigzagSides.containsSide(ZIGZAG_TOP) && zigzagHeight > 0) {
            drawHorizontalSide(pathZigzag, left, top, right, isTop = true)
        } else {
            pathZigzag.lineTo(left, top)
        }

        if (zigzagSides.containsSide(ZIGZAG_LEFT) && zigzagHeight > 0) {
            drawVerticalSide(pathZigzag, top, left, bottom, isLeft = true)
        } else {
            pathZigzag.lineTo(left, bottom)
        }

        if (zigzagSides.containsSide(ZIGZAG_BOTTOM) && zigzagHeight > 0) {
            drawHorizontalSide(pathZigzag, left, bottom, right, isTop = false)
        } else {
            pathZigzag.lineTo(right, bottom)
        }
    }

    private fun drawHorizontalSide(
        path: Path,
        left: Float,
        y: Float,
        right: Float,
        isTop: Boolean
    ) {
        val h = zigzagHeight
        val seed = 2 * h
        val width = right - left
        val count: Int = (width / seed).toInt()
        val diff = width - seed * count
        val sideDiff = diff / 2
        val halfSeed = seed / 2
        val innerHeight = if (isTop) y + h else y - h
        if (isTop) {
            for (i in count downTo 1) {
                val startSeed = i * seed + sideDiff + left.toInt()
                var endSeed = startSeed - seed
                if (i == 1) {
                    endSeed -= sideDiff
                }
                path.lineTo(startSeed - halfSeed, innerHeight)
                path.lineTo(endSeed, y)
            }
        } else {
            for (i in 0 until count) {
                var startSeed = i * seed + sideDiff + left.toInt()
                var endSeed = startSeed + seed
                if (i == 0) {
                    startSeed = left.toInt() + sideDiff
                } else if (i == count - 1) {
                    endSeed += sideDiff
                }
                path.lineTo(startSeed + halfSeed, innerHeight)
                path.lineTo(endSeed, y)
            }
        }
    }

    private fun drawVerticalSide(path: Path, top: Float, x: Float, bottom: Float, isLeft: Boolean) {
        val h = zigzagHeight
        val seed = 2 * h
        val width = bottom - top
        val count: Int = (width / seed).toInt()
        val diff = width - seed * count
        val sideDiff = diff / 2
        val halfSeed = seed / 2
        val innerHeight = if (isLeft) x + h else x - h
        if (!isLeft) {
            for (i in count downTo 1) {
                val startSeed = i * seed + sideDiff + top.toInt()
                var endSeed = startSeed - seed
                if (i == 1) {
                    endSeed -= sideDiff
                }
                path.lineTo(innerHeight, startSeed - halfSeed)
                path.lineTo(x, endSeed)
            }
        } else {
            for (i in 0 until count) {
                var startSeed = i * seed + sideDiff + top.toInt()
                var endSeed = startSeed + seed
                if (i == 0) {
                    startSeed = top.toInt() + sideDiff
                } else if (i == count - 1) {
                    endSeed += sideDiff
                }
                path.lineTo(innerHeight, startSeed + halfSeed)
                path.lineTo(x, endSeed)
            }
        }
    }

    companion object {
        private const val ZIGZAG_TOP = 0b0001       // 1
        private const val ZIGZAG_BOTTOM = 0b0010    // 2, default
        private const val ZIGZAG_RIGHT = 0b0100     // 4
        private const val ZIGZAG_LEFT = 0b1000      // 8
    }

}

private fun Int.containsSide(side: Int): Boolean {
    return (this or side) == this
}