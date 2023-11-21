package com.jummania.animations

import android.view.View
import androidx.viewpager.widget.ViewPager.PageTransformer
import kotlin.math.abs

/**
 * Created by Jummania on 21,November,2023.
 * Email: sharifuddinjumman@gmail.com
 * Dhaka, Bangladesh.
 */
internal class DepthSlide : PageTransformer {
    private val minScale = 0.75f

    // This method is called for each page during the transformation process.
    override fun transformPage(view: View, position: Float) {
        // Get the width of the page.
        val pageWidth: Int = view.width

        // Apply different transformations based on the position of the page.
        when {
            // If the page is to the left and not visible.
            position < -1 -> {
                // Set alpha to 0 to make it completely transparent.
                view.alpha = 0f
            }
            // If the page is within the visible range to the left or at the center.
            position <= 0 -> {
                // Set alpha to 1 to make it fully visible.
                view.alpha = 1f
                // Reset translation to 0, scaleX to 1, and scaleY to 1.
                view.translationX = 0f
                view.scaleX = 1f
                view.scaleY = 1f
            }
            // If the page is within the visible range to the right.
            position <= 1 -> {
                // Calculate alpha based on the position.
                view.alpha = 1 - position

                // Translate the page horizontally based on its width and position.
                view.translationX = pageWidth * -position

                // Calculate a scale factor based on the absolute position.
                val scaleFactor: Float = (minScale + (1 - minScale) * (1 - abs(position)))

                // Apply the scale factor to scaleX and scaleY.
                view.scaleX = scaleFactor
                view.scaleY = scaleFactor
            }
            // If the page is to the right and not visible.
            else -> {
                // Set alpha to 0 to make it completely transparent.
                view.alpha = 0f
            }
        }
    }
}
