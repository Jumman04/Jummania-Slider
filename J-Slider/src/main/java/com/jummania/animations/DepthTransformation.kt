package com.jummania.animations

import android.view.View
import androidx.viewpager.widget.ViewPager
import kotlin.math.abs

/**
 * Created by Jummania on 17,November,2023.
 * Email: sharifuddinjumman@gmail.com
 * Dhaka, Bangladesh.
 */
class DepthTransformation : ViewPager.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        if (position < -1)     // [-Infinity,-1)
        // This page is way off-screen to the left.
            page.alpha = 0f
        else if (position <= 0) {    // [-1,0]
            page.alpha = 1f
            page.translationX = 0f
            page.scaleX = 1f
            page.scaleY = 1f
        } else if (position <= 1) {    // (0,1]
            page.translationX = -position * page.width
            page.alpha = 1 - abs(position)
            page.scaleX = 1 - abs(position)
            page.scaleY = 1 - abs(position)
        } else     // (1,+Infinity]
        // This page is way off-screen to the right.
            page.alpha = 0f

    }
}