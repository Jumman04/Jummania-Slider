package com.jummania.animations

import android.view.View
import androidx.viewpager.widget.ViewPager.PageTransformer
import kotlin.math.abs
import kotlin.math.max

/**
 * Created by denzcoskun on 03,April,2023.
 * Email: denzcoskun@hotmail.com
 * Istanbul, TURKEY.
 */
class ZoomOut : PageTransformer {

    private val scale = 0.85f // Scale factor for zooming out
    private val alpha = 0.5f // Alpha value for fading

    override fun transformPage(view: View, position: Float) {
        val pageWidth: Int = view.width
        val pageHeight: Int = view.height

        when {
            position < -1 -> {
                // If the page is off-screen to the left, make it completely transparent
                view.alpha = 0f
            }

            position <= 1 -> {
                // If the page is within the visible range
                val scaleFactor = max(scale, 1 - abs(position))
                val vertMargin = pageHeight * (1 - scaleFactor) / 2
                val horzMargin = pageWidth * (1 - scaleFactor) / 2

                // Adjust translation and scale based on position
                if (position < 0) {
                    view.translationX = horzMargin - vertMargin / 2
                } else {
                    view.translationX = -horzMargin + vertMargin / 2
                }
                view.scaleX = scaleFactor
                view.scaleY = scaleFactor

                // Adjust alpha for fading effect
                view.alpha = alpha + (scaleFactor - scale) / (1 - scale) * (1 - alpha)
            }

            else -> {
                // If the page is off-screen to the right, make it completely transparent
                view.alpha = 0f
            }
        }
    }

}
