package com.jummania.animations

import android.view.View
import androidx.viewpager.widget.ViewPager.PageTransformer

/**
 * Created by Jummania on 21,November,2023.
 * Email: sharifuddinjumman@gmail.com
 * Dhaka, Bangladesh.
 */
internal class RotateDown : PageTransformer {

    // This method is called for each page during the transformation process.
    override fun transformPage(view: View, position: Float) {
        // Get the width of the view.
        val width = view.width
        // Calculate the rotation angle based on the position.
        val rotation = -15f * position

        // Set the pivot point to the center horizontally and to the top vertically.
        view.pivotX = width * 0.5f
        view.pivotY = 0f
        // Reset translationX to 0 to avoid horizontal movement.
        view.translationX = 0f
        // Apply the rotation around the pivot point.
        view.rotation = rotation
    }
}
