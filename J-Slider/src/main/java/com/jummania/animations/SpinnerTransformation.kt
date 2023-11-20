package com.jummania.animations

import android.view.View
import androidx.viewpager.widget.ViewPager
import kotlin.math.abs

/**
 * Created by Jummania on 17,November,2023.
 * Email: sharifuddinjumman@gmail.com
 * Dhaka, Bangladesh.
 */
class SpinnerTransformation : ViewPager.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        page.translationX = -position * page.width
        page.cameraDistance = 12000f
        if (position < 0.5 && position > -0.5) page.visibility = View.VISIBLE
        else page.visibility = View.INVISIBLE

        if (position < -1)      // [-Infinity,-1)
        // This page is way off-screen to the left.
            page.alpha = 0f
        else if (position <= 0) {    // [-1,0]
            page.alpha = 1f
            page.rotationY = 900 * (1 - abs(position) + 1)
        } else if (position <= 1) {    // (0,1]
            page.alpha = 1f
            page.rotationY = -900 * (1 - abs(position) + 1)
        } else    // (1,+Infinity]
        // This page is way off-screen to the right.
            page.alpha = 0f


    }
}