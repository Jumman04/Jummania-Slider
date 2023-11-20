package com.jummania

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.widget.LinearLayout
import com.jummania.types.InternalShapeIndicator
import com.jummania.types.IndicatorShapeTypes


/**
 * Custom LinearLayout that draws a colored circle.
 *
 * @param context The context in which the JLayout is created.
 */

internal abstract class JLayout(private val context: Context?, private val indicatorShapeTypes: IndicatorShapeTypes) : LinearLayout(context) {

    // Paint object used for drawing
    private val paint = Paint()
    // private val path by lazy { Path() }
    // private val indicatorShapeType = InternalShapeIndicator.CIRCLE
    init {
        // Initialize the paint style
        paint.style = Paint.Style.FILL
    }

    /**
     * Override the onDraw method to draw a colored circle.
     *
     * @param canvas The canvas on which to draw the circle.
     */
    override fun onDraw(canvas: Canvas) {
        // Draw a colored circle in the center of the layout
        InternalShapeIndicator.values()[indicatorShapeTypes.ordinal].drawShape(width, height, paint, canvas)
    }

    /**
     * Set the color of the circle.
     *
     * @param color The color to set for the circle.
     */
    fun setColor(color: Int) {
        paint.color = color
    }
}
