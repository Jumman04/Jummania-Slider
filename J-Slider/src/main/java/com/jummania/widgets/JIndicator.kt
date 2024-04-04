package com.jummania.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.view.View
import com.jummania.types.ShapeTypes

/**
 * Abstract class representing a custom layout with a colored shape indicator.
 *
 * This class serves as a base for creating custom indicator views with various shapes.
 * Subclasses must implement the [ShapeTypes] interface to define the shape of the indicator.
 *
 * Created by MD Abdulla and edited by Jummania on 20, November, 2023.
 * Email: sharifuddinjumman@gmail.com
 * Dhaka, Bangladesh.
 *
 * @param context The context in which the layout is created.
 * @param shapeTypes The shape type for the indicator.
 */
internal abstract class JIndicator(
    private val context: Context?, private val shapeTypes: ShapeTypes?
) : View(context) {

    // Paint object used for drawing
    private val paint by lazy {
        Paint().apply {
            // Setting the paint style to fill
            Paint.Style.FILL
        }
    }

    // Path object used for drawing shapes
    private val path by lazy { Path() }

    private var padding: Int = 0

    /**
     * Override the onDraw method to draw a colored shape in the center of the layout.
     *
     * This method is responsible for drawing the indicator shape on the canvas.
     * It translates the canvas to account for padding and delegates the shape drawing
     * to the [ShapeTypes.onDraw] method implemented by subclasses.
     *
     * @param canvas The canvas on which to draw the shape.
     */
    override fun onDraw(canvas: Canvas) {
        // Translate the canvas to handle padding
        canvas.translate(padding.toFloat(), padding.toFloat())

        // Draw the specified shape indicator with the given color
        // Adjust the width and height by subtracting padding
        shapeTypes?.onDraw?.invoke(width - 2 * padding, height - 2 * padding, paint, canvas, path)
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

    /**
     * Set padding for the custom view.
     *
     * Padding is applied to create space around the indicator shape.
     *
     */
    fun addPadding() {
        padding = 2
    }
}
