package com.jummania.j_slider.animations

import android.view.View
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.PageTransformer
import kotlin.math.abs

/**
 * Created by denzcoskun on 03,April,2023.
 * Email: denzcoskun@hotmail.com
 * Istanbul, TURKEY.
 */
class DepthSlide2 internal constructor(slider: ViewPager) : PageTransformer {

    init {
        slider.apply {
            clipChildren = false
            clipToPadding = false
            offscreenPageLimit = 3
            setPadding(55, 0, 55, 0)
        }

    }


    override fun transformPage(page: View, position: Float) {
        page.scaleY = 0.85f + (1 - abs(position)) * 0.15f
    }
}