package com.jummania.animations

import android.view.View
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.PageTransformer
import kotlin.math.abs

/**
 * Created by Jummania on 08,November,2023.
 * Email: sharifuddinjumman@gmail.com
 * Dhaka, Bangladesh.
 */
internal class DepthSlide2 internal constructor(slider: ViewPager) : PageTransformer {

    // Initialization block runs when an instance of DepthSlide2 is created.
    init {
        // Apply configuration to the provided ViewPager.
        slider.apply {
            clipChildren = false
            clipToPadding = false
            offscreenPageLimit = 3
            setPadding(55, 0, 55, 0)
        }
    }

    // This method is called for each page during the transformation process.
    override fun transformPage(page: View, position: Float) {
        // Apply a vertical scale transformation to create a depth effect.
        page.scaleY = 0.85f + (1 - abs(position)) * 0.15f
    }
}