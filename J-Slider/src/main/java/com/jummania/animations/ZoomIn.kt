package com.jummania.animations

import android.view.View
import androidx.viewpager.widget.ViewPager.PageTransformer
import kotlin.math.abs

/**
 * Created by Jummania on 21,November,2023.
 * Email: sharifuddinjumman@gmail.com
 * Dhaka, Bangladesh.
 */
internal class ZoomIn : PageTransformer {

    override fun transformPage(view: View, position: Float) {
        // Calculate the scale based on the position.
        val scale: Float = if (position < 0) position + 1f else abs(1f - position)
        // Set the scale for both X and Y dimensions.
        view.scaleX = scale
        view.scaleY = scale
        // Set the pivot point for scaling at the center of the view.
        view.pivotX = view.width * 0.5f
        view.pivotY = view.height * 0.5f
        // Adjust the alpha based on the position for a fade-in effect.
        view.alpha = if (position < -1f || position > 1f) 0f else 1f - (scale - 1f)
    }

}
