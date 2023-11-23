package com.jummania.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.widget.LinearLayout
import com.jummania.types.ShapeTypes
import kotlin.math.min

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
    private val path by lazy { Path() }

    /**
     * Override the onDraw method to draw a colored shape in the center of the layout.
     *
     * @param canvas The canvas on which to draw the shape.
     */
    override fun onDraw(canvas: Canvas) {
        // Draw the specified shape indicator with the given color
        // Draw a colored circle in the center of the layout
        when (shapeTypes) {
            ShapeTypes.CIRCLE -> {
                canvas.drawCircle(
                    (width / 2f), height / 2f, (min(width, height) / 2f), paint
                )
            }

            ShapeTypes.HEART -> {
                // Starting point
                path.moveTo(width / 2f, height / 5f)

                // Upper left path
                path.cubicTo(
                    5 * width / 14f, 0f, 0f, height / 15f, width / 28f, 2 * height / 5f
                )

                // Lower left path
                path.cubicTo(
                    width / 14f,
                    2 * height / 3f,
                    3 * width / 7f,
                    5 * height / 6f,
                    width / 2f,
                    height.toFloat()
                )

                // Lower right path
                path.cubicTo(
                    4 * width / 7f,
                    5 * height / 6f,
                    13 * width / 14f,
                    2 * height / 3f,
                    27 * width / 28f,
                    2 * height / 5f
                )

                // Upper right path
                path.cubicTo(
                    width.toFloat(), height / 15f, 9 * width / 14f, 0f, width / 2f, height / 5f
                )

                canvas.drawPath(path, paint)
            }

            ShapeTypes.SQUARE -> {
                canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
            }

            ShapeTypes.STAR -> {
                paint.isAntiAlias = true
                paint.style = Paint.Style.STROKE

                var mid = (width / 2).toFloat()
                val min = min(width, height).toFloat()
                val fat = min / 17
                val half = min / 2
                val rad = half - fat
                mid -= half

                paint.strokeWidth = fat
                paint.style = Paint.Style.STROKE

                canvas.drawCircle(mid + half, half, rad, paint)

                path.reset()

                paint.style = Paint.Style.FILL


                // top left


                // top left
                path.moveTo(mid + half * 0.5f, half * 0.84f)
                // top right
                // top right
                path.lineTo(mid + half * 1.5f, half * 0.84f)
                // bottom left
                // bottom left
                path.lineTo(mid + half * 0.68f, half * 1.45f)
                // top tip
                // top tip
                path.lineTo(mid + half * 1.0f, half * 0.5f)
                // bottom right
                // bottom right
                path.lineTo(mid + half * 1.32f, half * 1.45f)
                // top left
                // top left
                path.lineTo(mid + half * 0.5f, half * 0.84f)

                path.close()
                canvas.drawPath(path, paint)
            }
        }
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

