package com.jummania.animations

import android.view.View
import androidx.viewpager.widget.ViewPager.PageTransformer
import kotlin.math.abs
import kotlin.math.min

/**
 * Created by Jummania on 21,November,2023.
 * Email: sharifuddinjumman@gmail.com
 * Dhaka, Bangladesh.
 */
internal class ForegroundToBackground : PageTransformer {

    // This method is called for each page during the transformation process.
    override fun transformPage(view: View, position: Float) {
        // Get the height and width of the view.
        val height: Float = view.height.toFloat()
        val width: Float = view.width.toFloat()

        // Calculate the scale factor based on the position to create a zoom-in/zoom-out effect.
        val scale: Float = min(if (position > 0) 1f else abs(1f + position), 1f)

        // Set the scale for both X and Y axes to create uniform scaling.
        view.scaleX = scale
        view.scaleY = scale

        // Set the pivot points for scaling at the center of the page.
        view.pivotX = width * 0.5f
        view.pivotY = height * 0.5f

        // Translate the page horizontally based on the position to create a sliding effect.
        view.translationX = if (position > 0) width * position else -width * position * 0.25f
    }
}
