package com.jummania.animations

import android.view.View
import androidx.viewpager.widget.ViewPager.PageTransformer

/**
 * Created by denzcoskun on 03,April,2023.
 * Email: denzcoskun@hotmail.com
 * Istanbul, TURKEY.
 */
class CubeIn : PageTransformer {

    // This method is called for each page during the transformation process.
    override fun transformPage(view: View, position: Float) {
        // Set the pivot point for rotation at the left edge of the view if the page is to the right (position > 0),
        // or at the right edge of the view if the page is to the left (position <= 0).
        view.pivotX = if (position > 0) 0f else view.width.toFloat()

        // Set the pivot point for rotation at the top edge of the view.
        view.pivotY = 0f

        // Apply the rotation around the Y-axis to create the cube-in effect.
        view.rotationY = -90f * position
    }
}
