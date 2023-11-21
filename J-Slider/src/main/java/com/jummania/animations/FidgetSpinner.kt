package com.jummania.animations

import android.view.View
import androidx.viewpager.widget.ViewPager.PageTransformer
import kotlin.math.abs

/**
 * Created by Jummania on 21,November,2023.
 * Email: sharifuddinjumman@gmail.com
 * Dhaka, Bangladesh.
 */
internal class FidgetSpinner : PageTransformer {

    // This method is called for each page during the transformation process.
    override fun transformPage(view: View, position: Float) {
        // Translate the page horizontally based on its position.
        view.translationX = -position * view.width

        // Handle visibility and scale based on the absolute position.
        if (abs(position) < 0.5) {
            view.visibility = View.VISIBLE
            view.scaleX = 1 - abs(position)
            view.scaleY = 1 - abs(position)
        } else if (abs(position) > 0.5) {
            view.visibility = View.GONE
        }

        // Apply rotation based on the position for a fidget spinner effect.
        when {
            position < -1 -> {
                view.alpha = 0f
            }

            position <= 0 -> {
                view.alpha = 1f
                // Rotate clockwise for the left half of the spinner.
                view.rotation =
                    36000 * (abs(position) * abs(position) * abs(position) * abs(position) * abs(
                        position
                    ) * abs(position) * abs(position))
            }

            position <= 1 -> {
                view.alpha = 1f
                // Rotate counterclockwise for the right half of the spinner.
                view.rotation =
                    -36000 * (abs(position) * abs(position) * abs(position) * abs(position) * abs(
                        position
                    ) * abs(position) * abs(position))
            }

            else -> {
                view.alpha = 0f
            }
        }
    }
}
