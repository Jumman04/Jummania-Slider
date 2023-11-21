package com.jummania.animations

import android.graphics.Camera
import android.graphics.Matrix
import android.view.View
import androidx.viewpager.widget.ViewPager.PageTransformer
import kotlin.math.abs

/**
 * Created by Jummania on 08,November,2023.
 * Email: sharifuddinjumman@gmail.com
 * Dhaka, Bangladesh.
 */

internal class TabletSlide : PageTransformer {
    // Matrix and Camera objects used for handling 3D transformations.
    private val matrix: Matrix = Matrix()
    private val camera: Camera = Camera()

    // Float array to store points.
    private val floats = FloatArray(2)

    override fun transformPage(page: View, position: Float) {
        // Calculate the rotation angle based on the position.
        val rotation: Float = (if (position < 0) 30f else -30f) * abs(position)
        // Translate the page horizontally.
        page.translationX = getOffsetX(rotation, page.width, page.height)
        // Set the pivot point for the rotation.
        page.pivotX = page.width * 0.5f
        page.pivotY = 0F
        // Apply the Y-axis rotation.
        page.rotationY = rotation
    }

    private fun getOffsetX(rotation: Float, width: Int, height: Int): Float {
        // Reset the matrix.
        matrix.reset()
        // Save the camera state.
        camera.save()
        // Rotate the camera around the Y-axis.
        camera.rotateY(abs(rotation))
        // Get the transformation matrix from the camera.
        camera.getMatrix(matrix)
        // Restore the camera state.
        camera.restore()
        // Pre-translate the matrix to handle the pivot point.
        matrix.preTranslate(-width * 0.5f, -height * 0.5f)
        matrix.postTranslate(width * 0.5f, height * 0.5f)
        // Map points to the matrix.
        floats[0] = width.toFloat()
        floats[1] = height.toFloat()
        matrix.mapPoints(floats)
        // Calculate and return the offset in the X-axis direction.
        return (width - floats[0]) * if (rotation > 0.0f) 1.0f else -1.0f
    }
}
