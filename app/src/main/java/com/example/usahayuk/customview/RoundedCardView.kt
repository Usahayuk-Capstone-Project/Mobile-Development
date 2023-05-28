package com.example.usahayuk.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import androidx.cardview.widget.CardView

class RoundedCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {
    private val cornerRadius = 10f // Ukuran sudut melengkung (dalam piksel)
    private val shadowPaint = Paint().apply {
        isAntiAlias = true
        setShadowLayer(8f, 0f, 4f, 0x66000000) // Bayangan (shadow)
    }

    init {
        setLayerType(LAYER_TYPE_SOFTWARE, null)
    }

    override fun onDraw(canvas: Canvas) {
        val cornerPath = Path()
        val rect = RectF(0f, 0f, width.toFloat(), height.toFloat())
        cornerPath.addRoundRect(rect, cornerRadius, cornerRadius, Path.Direction.CW)
        canvas.clipPath(cornerPath)

        super.onDraw(canvas)
        canvas.drawPath(cornerPath, shadowPaint)
    }
}