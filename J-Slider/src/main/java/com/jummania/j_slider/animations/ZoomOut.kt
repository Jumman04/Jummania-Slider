package com.jummania.j_slider.animations

import android.view.View
import androidx.viewpager.widget.ViewPager.PageTransformer
import kotlin.math.abs
import kotlin.math.max

/**
 * Created by denzcoskun on 03,April,2023.
 * Email: denzcoskun@hotmail.com
 * Istanbul, TURKEY.
 */
class ZoomOut: PageTransformer {

    private val scale = 0.85f
    private val alpha = 0.5f

    override fun transformPage(view: View, position: Float) {
        val pageWidth: Int = view.width
        val pageHeight: Int = view.height

        when {
            position < -1 -> {
                view.alpha = 0f
            }
            position <= 1 -> {
                val scaleFactor = max(scale, 1 - abs(position))
                val vertMargin = pageHeight * (1 - scaleFactor) / 2
                val horzMargin = pageWidth * (1 - scaleFactor) / 2
                if (position < 0) {
                    view.translationX = horzMargin - vertMargin / 2
                } else {
                    view.translationX = -horzMargin + vertMargin / 2
                }
                view.scaleX = scaleFactor
                view.scaleY = scaleFactor
                view.alpha = alpha +
                        (scaleFactor - scale) /
                        (1 - scale) * (1 - alpha)
            }
            else -> {
                view.alpha = 0f
            }
        }
    }

}