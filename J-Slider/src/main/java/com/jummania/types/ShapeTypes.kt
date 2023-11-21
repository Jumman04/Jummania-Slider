package com.jummania.types

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path

/**
 * * Sealed class representing different shape types with associated drawing functions.
 *  * Created by Jummania on 21,November,2023.
 *  * Specially thanks to MD Abdullah.
 *  * Email: sharifuddinjumman@gmail.com
 *  * Dhaka, Bangladesh.
 *
 * Sets the shape type for the indicator dots in the JSlider.
 * The available shapes are defined by the [ShapeTypes] sealed class.
 *
 */
sealed class ShapeTypes(val draw: (Int, Int, Paint, Canvas, Path) -> Unit) {

    // Data object representing a CIRCLE shape
    data object CIRCLE : ShapeTypes({ width, height, paint, canvas, _ ->
        canvas.drawCircle(
            (width / 2f), height / 2f, (kotlin.math.min(width, height) / 2f), paint
        )
    })

    // Data object representing a HEART shape
    data object HEART : ShapeTypes({ width, height, paint, canvas, path ->
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
    })

    // Data object representing a SQUARE shape
    data object SQUARE : ShapeTypes({ width, height, paint, canvas, _ ->
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
    })

    // Data object representing a STAR shape
    data object STAR : ShapeTypes({ width, height, paint, canvas, path ->
        paint.isAntiAlias = true
        paint.style = Paint.Style.STROKE

        var mid = (width / 2).toFloat()
        val min = kotlin.math.min(width, height).toFloat()
        val fat = min / 17
        val half = min / 2
        val rad = half - fat
        mid -= half

        paint.strokeWidth = fat
        paint.style = Paint.Style.STROKE

        canvas.drawCircle(mid + half, half, rad, paint)

        path.reset()

        paint.style = Paint.Style.FILL

        // Drawing a star using path
        path.moveTo(mid + half * 0.5f, half * 0.84f)
        path.lineTo(mid + half * 1.5f, half * 0.84f)
        path.lineTo(mid + half * 0.68f, half * 1.45f)
        path.lineTo(mid + half * 1.0f, half * 0.5f)
        path.lineTo(mid + half * 1.32f, half * 1.45f)
        path.lineTo(mid + half * 0.5f, half * 0.84f)

        path.close()
        canvas.drawPath(path, paint)
    })

    companion object {
        /**
         * Function to get a ShapeTypes based on the specified position.
         *
         * @param position The position representing the shape type.
         * @return The corresponding ShapeTypes object.
         */
        fun getTypes(position: Int): ShapeTypes {
            return when (position) {
                0 -> CIRCLE
                1 -> HEART
                2 -> SQUARE
                3 -> STAR

                // Default to CIRCLE if the position is not recognized
                else -> CIRCLE
            }
        }
    }
}
