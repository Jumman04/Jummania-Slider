package com.jummania.animations

import android.view.View
import androidx.viewpager.widget.ViewPager.PageTransformer
import kotlin.math.abs
import kotlin.math.max

/**
 * Created by Jummania on 21,November,2023.
 * Email: sharifuddinjumman@gmail.com
 * Dhaka, Bangladesh.
 */
internal class Toss : PageTransformer {

    override fun transformPage(view: View, position: Float) {
        // Translate the page horizontally based on the position.
        view.translationX = -position * view.width
        // Set the camera distance for a 3D effect.
        view.cameraDistance = 20000f

        // Toggle visibility based on the position.
        if (position < 0.5 && position > -0.5) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.INVISIBLE
        }

        when {
            // If the position is less than -1, set alpha to 0 (page is off-screen).
            position < -1 -> {
                view.alpha = 0f
            }
            // If the position is between -1 and 0, apply transformations for the left half.
            position <= 0 -> {
                view.alpha = 1f
                // Scale the page while ensuring a minimum scale of 0.4.
                view.scaleX = max(0.4f, 1 - abs(position))
                view.scaleY = max(0.4f, 1 - abs(position))
                // Rotate the page around the X-axis with a factor of 1080.
                view.rotationX = 1080 * (1 - abs(position) + 1)
                // Translate the page vertically.
                view.translationY = -1000 * abs(position)
            }
            // If the position is between 0 and 1, apply transformations for the right half.
            position <= 1 -> {
                view.alpha = 1f
                // Scale the page while ensuring a minimum scale of 0.4.
                view.scaleX = max(0.4f, 1 - abs(position))
                view.scaleY = max(0.4f, 1 - abs(position))
                // Rotate the page around the X-axis with a factor of -1080.
                view.rotationX = -1080 * (1 - abs(position) + 1)
                // Translate the page vertically.
                view.translationY = -1000 * abs(position)
            }
            // If the position is greater than 1, set alpha to 0 (page is off-screen).
            else -> {
                view.alpha = 0f
            }
        }
    }
}
