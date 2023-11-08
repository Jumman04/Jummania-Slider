package com.jummania.j_slider

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.widget.LinearLayout
import kotlin.math.min

 class JLayout(context: Context?) : LinearLayout(context) {
    private val paint = Paint()

    init {
        paint.style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawCircle(
            (width / 2).toFloat(), height.toFloat() / 2, (min(width, height) / 2).toFloat(), paint
        )
    }

    fun setColor(color: Int) {
        paint.color = color
        invalidate() // Request a redraw to reflect the changes
    }
}
