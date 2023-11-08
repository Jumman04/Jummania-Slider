package com.jummania.j_slider.animations

import android.graphics.Camera
import android.graphics.Matrix
import android.view.View
import androidx.viewpager.widget.ViewPager.PageTransformer

/**
 * Created by Jummania on 08,November,2023.
 * Email: sharifuddinjumman@gmail.com
 * Dhaka, Bangladesh.
 */

class TabletSlide : PageTransformer {
    private val matrix: Matrix = Matrix()
    private val camera: Camera = Camera()
    private val floats = FloatArray(2)
    override fun transformPage(page: View, position: Float) {
        val rotation: Float = (if (position < 0) 30f else -30f) * Math.abs(position)
        page.translationX = getOffsetX(rotation, page.width, page.height)
        page.pivotX = page.width * 0.5f
        page.pivotY = 0F
        page.rotationY = rotation
    }

    private fun getOffsetX(rotation: Float, width: Int, height: Int): Float {
        matrix.reset()
        camera.save()
        camera.rotateY(Math.abs(rotation))
        camera.getMatrix(matrix)
        camera.restore()
        matrix.preTranslate(-width * 0.5f, -height * 0.5f)
        matrix.postTranslate(width * 0.5f, height * 0.5f)
        floats[0] = width.toFloat()
        floats[1] = height.toFloat()
        matrix.mapPoints(floats)
        return (width - floats[0]) * if (rotation > 0.0f) 1.0f else -1.0f
    }
}