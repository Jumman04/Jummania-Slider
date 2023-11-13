package com.jummania.animations

import android.view.View
import androidx.viewpager.widget.ViewPager.PageTransformer
import kotlin.math.abs

/**
 * Created by denzcoskun on 03,April,2023.
 * Email: denzcoskun@hotmail.com
 * Istanbul, TURKEY.
 */
class Gate : PageTransformer {

    // This method is called for each page during the transformation process.
    override fun transformPage(view: View, position: Float) {
        // Translate the page horizontally based on the position.
        view.translationX = -position * view.width

        // Perform different transformations based on the position.
        when {
            position < -1 -> {
                // If the page is to the left and not visible, set alpha to 0 (completely transparent).
                view.alpha = 0f
            }

            position <= 0 -> {
                // If the page is to the left and becoming visible, set alpha to 1 (completely opaque).
                view.alpha = 1f
                // Set the pivot point to the left edge and apply a rotation around the Y-axis.
                view.pivotX = 0f
                view.rotationY = 90 * abs(position)
            }

            position <= 1 -> {
                // If the page is to the right and becoming visible, set alpha to 1 (completely opaque).
                view.alpha = 1f
                // Set the pivot point to the right edge and apply a rotation around the Y-axis.
                view.pivotX = view.width.toFloat()
                view.rotationY = -90 * abs(position)
            }

            else -> {
                // If the page is to the right and not visible, set alpha to 0 (completely transparent).
                view.alpha = 0f
            }
        }
    }
}
