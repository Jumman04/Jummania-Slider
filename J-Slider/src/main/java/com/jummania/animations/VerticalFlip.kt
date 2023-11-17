package com.jummania.animations

import android.view.View
import androidx.viewpager.widget.ViewPager

/**
 * Created by Jummania on 17,November,2023.
 * Email: sharifuddinjumman@gmail.com
 * Dhaka, Bangladesh.
 */
class VerticalFlip : ViewPager.PageTransformer {
    override fun transformPage(view: View, position: Float) {
        view.cameraDistance = 20000f
        view.rotationX = 180 * position
    }
}