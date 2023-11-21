package com.jummania.animations

import android.view.View
import androidx.viewpager.widget.ViewPager.PageTransformer

/**
 * Created by Jummania on 21,November,2023.
 * Email: sharifuddinjumman@gmail.com
 * Dhaka, Bangladesh.
 */
internal class CubeOut : PageTransformer {

    // This method is called for each page during the transformation process.
    override fun transformPage(view: View, position: Float) {
        // Set the pivot point for rotation at the right edge of the view if the page is to the left (position < 0),
        // or at the left edge of the view if the page is to the right (position >= 0).
        view.pivotX = if (position < 0f) view.width.toFloat() else 0f

        // Set the pivot point for rotation at the vertical center of the view.
        view.pivotY = view.height * 0.5f

        // Apply the rotation around the Y-axis to create the cube-out effect.
        view.rotationY = 90f * position
    }
}
