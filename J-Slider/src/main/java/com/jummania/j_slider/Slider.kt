package com.jummania.j_slider

import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.widget.LinearLayout
import android.widget.Scroller
import androidx.viewpager.widget.ViewPager
import com.jummania.j_slider.Slider.BooleanObject.autoSlidingBoolean
import com.jummania.j_slider.Slider.BooleanObject.indicatorBoolean
import com.jummania.j_slider.Slider.BooleanObject.isDragging
import com.jummania.j_slider.Slider.IntObject.defaultIndicatorColor
import com.jummania.j_slider.Slider.IntObject.selectedIndicatorColor
import com.jummania.j_slider.Slider.IntObject.size
import com.jummania.j_slider.Slider.IntObject.slidingDuration

/**
 * Created by Jummania on 08,November,2023.
 * Email: sharifuddinjumman@gmail.com
 * Dhaka, Bangladesh.
 */

internal class Slider(context: Context) : ViewPager(context) {

    val updateHandler by lazy {
        Handler(Looper.getMainLooper())
    }
    private lateinit var update: Runnable

    private object IntObject {
        var slidingDuration: Long = 1555
        var size = 30
        var selectedIndicatorColor = Color.WHITE
        var defaultIndicatorColor = Color.parseColor("#80ffffff")
    }

    private object BooleanObject {
        var isDragging: Boolean = false
        var indicatorBoolean = true
        var autoSlidingBoolean = true
    }


    fun setSlider(slider: JSlider.Slide, dotIndicatorLayout: LinearLayout) {
        if (slider.count > 0) {
            super.setAdapter(slider)

            val sliders = slider.count
            val dots: MutableList<JLayout> by lazy { mutableListOf() }

            if (indicatorBoolean) {
                val dotLayoutParams = LinearLayout.LayoutParams(size, size)
                for (i in 0 until sliders) {
                    val dot = JLayout(context)
                    dotLayoutParams.marginStart = 6
                    dotLayoutParams.marginEnd = 6
                    dot.layoutParams = dotLayoutParams
                    dot.setBackgroundResource(R.drawable.indicator)
                    dot.setColor(defaultIndicatorColor)

                    dotIndicatorLayout.addView(dot)
                    dots.add(dot)
                }
                if (dots.isNotEmpty()) dots[0].setColor(selectedIndicatorColor)

            }

            update = Runnable {
                if (!isDragging && autoSlidingBoolean) {
                    var currentPage: Int = currentItem
                    if (currentPage == sliders - 1) currentPage = 0
                    else currentPage += 1
                    setCurrentItem(currentPage, true)
                }
            }

            addOnPageChangeListener(object : OnPageChangeListener {
                override fun onPageScrolled(
                    position: Int, positionOffset: Float, positionOffsetPixels: Int
                ) {

                }

                override fun onPageSelected(position: Int) {
                    if (indicatorBoolean && dots.isNotEmpty()) {

                        for (dot in dots) dot.setColor(defaultIndicatorColor)
                        dots[position].setColor(selectedIndicatorColor)
                    }
                    if (autoSlidingBoolean) {
                        updateHandler.removeCallbacks(update)
                        updateHandler.postDelayed(update, slidingDuration)
                    }
                }

                override fun onPageScrollStateChanged(state: Int) {
                    if (isDragging && state != SCROLL_STATE_DRAGGING && autoSlidingBoolean) {
                        updateHandler.removeCallbacks(update)
                        updateHandler.postDelayed(update, slidingDuration)
                    }
                    isDragging = state == SCROLL_STATE_DRAGGING
                }
            })

            try {
                val viewPagerScroller = ViewPager::class.java.getDeclaredField("mScroller")
                viewPagerScroller.isAccessible = true
                viewPagerScroller[this] = MyScroller(context)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    private inner class MyScroller(context: Context?) : Scroller(context) {
        override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
            super.startScroll(startX, startY, dx, dy, slidingDuration.toInt())
        }
    }

    fun setSlidingDuration(duration: Long) {
        slidingDuration = duration
    }

    fun setIndicatorSize(indicatorSize: Int) {
        size = indicatorSize
    }

    fun setIndicatorColor(defaultColor: Int, selectedColor: Int) {
        defaultIndicatorColor = defaultColor
        selectedIndicatorColor = selectedColor
    }

    fun enableIndicator(boolean: Boolean) {
        indicatorBoolean = boolean
    }

    fun enableAutoSliding(boolean: Boolean) {
        autoSlidingBoolean = boolean
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        if (hasWindowFocus && autoSlidingBoolean) {
            updateHandler.removeCallbacks(update)
            updateHandler.postDelayed(update, slidingDuration)
        } else updateHandler.removeCallbacks(update)
        super.onWindowFocusChanged(hasWindowFocus)
    }

}