package com.jummania.animations

import android.view.View
import androidx.viewpager.widget.ViewPager
import kotlin.math.abs

/**
 * Created by Jummania on 17,November,2023.
 * Email: sharifuddinjumman@gmail.com
 * Dhaka, Bangladesh.
 */
class Spinner : ViewPager.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        val angle = Math.toRadians((90 * position).toDouble())
        page.translationX = -position * page.width
        page.scaleX = 0.7f + abs(position) / 3
        page.scaleY = 0.7f + abs(position) / 3
        page.rotation = angle.toFloat()
    }
}