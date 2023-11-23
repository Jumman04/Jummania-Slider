package com.jummania.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.widget.LinearLayout
import com.jummania.types.ShapeTypes

/**
 * Abstract class representing a custom layout with a colored shape indicator.
 *
 *  * Created by MD Abdulla and edited by Jummania on 20,November,2023.
 *  * Email: sharifuddinjumman@gmail.com
 *  * Dhaka, Bangladesh.
 *
 * @param context The context in which the layout is created.
 * @param shapeTypes The shape type for the indicator.
 */
internal abstract class JIndicator(
    private val context: Context?, private val shapeTypes: ShapeTypes
) : LinearLayout(context) {

    // Paint object used for drawing
    private val paint by lazy {
        Paint().apply {
            // Setting the paint style to fill
            Paint.Style.FILL
        }
    }

    // Path object used for drawing shapes
    // private val path by lazy { Path() }

    /**
     * Override the onDraw method to draw a colored shape in the center of the layout.
     *
     * @param canvas The canvas on which to draw the shape.
     */
    override fun onDraw(canvas: Canvas) {
        // Draw the specified shape indicator with the given color
        // Draw a colored circle in the center of the layout
        shapeTypes.draw(width, height, paint, canvas)
    }

    /**
     * Set the color of the shape.
     *
     * @param color The color to set for the shape.
     */
    fun setColor(color: Int) {
        // Set the color of the paint object
        paint.color = color
    }
}

