package com.jummania.animations

import android.view.View
import androidx.viewpager.widget.ViewPager.PageTransformer

/**
 * Created by Jummania on 21,November,2023.
 * Email: sharifuddinjumman@gmail.com
 * Dhaka, Bangladesh.
 */
internal class RotateUp : PageTransformer {

    // This method is called for each page during the transformation process.
    override fun transformPage(view: View, position: Float) {
        // Get the width and height of the view.
        val width = view.width
        val height = view.height
        // Calculate the rotation angle based on the position, with an additional scaling factor.
        val rotation = -15f * position * -1.25f

        // Set the pivot point to the center horizontally and to the bottom vertically.
        view.pivotX = width * 0.5f
        view.pivotY = height.toFloat()
        // Apply the calculated rotation around the specified pivot point, creating an upward rotation effect.
        view.rotation = rotation
    }
}
