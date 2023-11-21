package com.jummania.animations

import android.view.View
import androidx.viewpager.widget.ViewPager
import kotlin.math.abs

/**
 * Created by Jummania on 17,November,2023.
 * Email: sharifuddinjumman@gmail.com
 * Dhaka, Bangladesh.
 */
internal class DepthZoomOut : ViewPager.PageTransformer {
    override fun transformPage(view: View, position: Float) {
        val scaleFactor = 0.75f.coerceAtLeast(1 - abs(position))
        val alpha = 1 - abs(position)

        view.translationY = view.height * -position
        view.alpha = alpha
        view.scaleX = scaleFactor
        view.scaleY = scaleFactor
    }
}