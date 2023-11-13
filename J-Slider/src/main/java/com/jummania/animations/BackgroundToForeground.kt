package com.jummania.animations

import android.view.View
import androidx.viewpager.widget.ViewPager.PageTransformer
import kotlin.math.abs
import kotlin.math.min

/**
 * Created by denzcoskun on 03,April,2023.
 * Email: denzcoskun@hotmail.com
 * Istanbul, TURKEY.
 */
class BackgroundToForeground : PageTransformer {

    // This method is called for each page during the transformation process.
    override fun transformPage(view: View, position: Float) {
        // Get the height and width of the view
        val height: Float = view.height.toFloat()
        val width: Float = view.width.toFloat()

        // Calculate the scale based on the position
        val scale: Float = min(if (position < 0) 1f else abs(1f - position), 1f)

        // Apply the scale and pivot properties to create the animation effect
        view.scaleX = scale
        view.scaleY = scale

        // Set the pivot point for scaling at the center of the view
        view.pivotX = width * 0.5f
        view.pivotY = height * 0.5f

        // Translate the view horizontally based on the position for a parallax effect
        // If position is negative (page to the left), translate to the left; otherwise, don't translate (position >= 0)
        view.translationX = if (position < 0) width * position else -width * position * 0.25f
    }
}
