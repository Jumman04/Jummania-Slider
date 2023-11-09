package com.jummania.j_slider

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Scroller
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.jummania.j_slider.JSlider.Slider.BooleanObject.autoSlidingBoolean
import com.jummania.j_slider.JSlider.Slider.BooleanObject.indicatorBoolean
import com.jummania.j_slider.JSlider.Slider.BooleanObject.isDragging
import com.jummania.j_slider.JSlider.Slider.IntObject.defaultIndicatorColor
import com.jummania.j_slider.JSlider.Slider.IntObject.selectedIndicatorColor
import com.jummania.j_slider.JSlider.Slider.IntObject.size
import com.jummania.j_slider.JSlider.Slider.IntObject.slidingDuration
import com.jummania.j_slider.animations.AnimationTypes
import com.jummania.j_slider.animations.BackgroundToForeground
import com.jummania.j_slider.animations.CubeIn
import com.jummania.j_slider.animations.CubeOut
import com.jummania.j_slider.animations.DepthSlide
import com.jummania.j_slider.animations.DepthSlide2
import com.jummania.j_slider.animations.FidgetSpinner
import com.jummania.j_slider.animations.FlipHorizontal
import com.jummania.j_slider.animations.FlipVertical
import com.jummania.j_slider.animations.ForegroundToBackground
import com.jummania.j_slider.animations.Gate
import com.jummania.j_slider.animations.RotateDown
import com.jummania.j_slider.animations.RotateUp
import com.jummania.j_slider.animations.TabletSlide
import com.jummania.j_slider.animations.Toss
import com.jummania.j_slider.animations.ZoomIn
import com.jummania.j_slider.animations.ZoomOut
import kotlin.math.min

/**
 * Created by Jummania on 08,November,2023.
 * Email: sharifuddinjumman@gmail.com
 * Dhaka, Bangladesh.
 */

class JSlider @JvmOverloads constructor(
    context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    /**
     * Created by Jummania on 08,November,2023.
     * Email: sharifuddinjumman@gmail.com
     * Dhaka, Bangladesh.
     */

    private val jSlider: Slider by lazy {
        Slider(context)
    }
    private val dotIndicatorLayout: LinearLayout by lazy {
        LinearLayout(context).apply {
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT
            )
            (layoutParams as LayoutParams).addRule(ALIGN_PARENT_BOTTOM)
            gravity = Gravity.CENTER
            orientation = LinearLayout.HORIZONTAL
        }
    }

    init {
        val typedArray: TypedArray = context.theme.obtainStyledAttributes(
            attrs, R.styleable.JSlider, defStyleAttr, defStyleAttr
        )


        setSlidingDuration(
            typedArray.getInt(R.styleable.JSlider_slidingDuration, 1555).toLong()
        )
        setIndicatorSize(
            typedArray.getDimensionPixelSize(
                R.styleable.JSlider_indicatorSize, 30
            )
        )
        setSliderPadding(
            typedArray.getDimensionPixelSize(R.styleable.JSlider_sliderPaddingLeft, 0),
            typedArray.getDimensionPixelSize(R.styleable.JSlider_sliderPaddingTop, 0),
            typedArray.getDimensionPixelSize(R.styleable.JSlider_sliderPaddingRight, 0),
            typedArray.getDimensionPixelSize(R.styleable.JSlider_sliderPaddingBottom, 0)
        )

        enableAutoSliding(typedArray.getBoolean(R.styleable.JSlider_enableAutoSliding, true))
        enableIndicator(typedArray.getBoolean(R.styleable.JSlider_enableIndicator, true))

        setIndicatorColor(
            typedArray.getColor(
                R.styleable.JSlider_defaultIndicatorColor, Color.parseColor("#90ffffff")
            ), typedArray.getColor(R.styleable.JSlider_selectedIndicatorColor, Color.WHITE)
        )

        dotIndicatorLayout.setPadding(
            0,
            0,
            0,
            typedArray.getDimensionPixelSize(R.styleable.JSlider_indicatorPaddingBottom, 55)
        )
    }

    fun setSlider(slider: Slide) {
        addView(jSlider)
        addView(dotIndicatorLayout)

        jSlider.setSlider(slider, dotIndicatorLayout)
    }

    abstract class Slide : PagerAdapter() {

        /**
         * Created by Jummania on 08,November,2023.
         * Email: sharifuddinjumman@gmail.com
         * Dhaka, Bangladesh.
         */
        abstract fun getView(layoutInflater: LayoutInflater, parent: ViewGroup): View
        abstract fun onSliderCreate(view: View, position: Int)
        override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`
        override fun instantiateItem(parent: ViewGroup, position: Int): View {
            val view = getView(LayoutInflater.from(parent.context), parent)
            onSliderCreate(view, position)
            parent.addView(view)
            return view
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }
    }

    fun setSlidingDuration(slidingDuration: Long) {
        exception()
        jSlider.setSlidingDuration(slidingDuration)
    }

    fun setIndicatorSize(size: Int) {
        exception()
        jSlider.setIndicatorSize(size)
    }

    fun setIndicatorColor(defaultColor: Int, selectedColor: Int) {
        exception()
        jSlider.setIndicatorColor(defaultColor, selectedColor)
    }

    fun enableIndicator(boolean: Boolean) {
        exception()
        jSlider.enableIndicator(boolean)
    }

    fun enableAutoSliding(boolean: Boolean) {
        exception()
        jSlider.enableAutoSliding(boolean)
    }

    fun setPageTransformer(boolean: Boolean, pageTransformer: ViewPager.PageTransformer) {
        jSlider.setPageTransformer(boolean, pageTransformer)
    }

    fun setSliderPadding(left: Int, top: Int, right: Int, bottom: Int) {
        jSlider.setPadding(left, top, right, bottom)
    }


    fun setSlideAnimation(animationType: AnimationTypes) {
        when (animationType) {
            AnimationTypes.ZOOM_IN -> jSlider.setPageTransformer(true, ZoomIn())


            AnimationTypes.ZOOM_OUT -> jSlider.setPageTransformer(true, ZoomOut())


            AnimationTypes.DEPTH_SLIDE -> jSlider.setPageTransformer(true, DepthSlide())

            AnimationTypes.DEPTH_SLIDE2 -> jSlider.setPageTransformer(
                true, DepthSlide2(jSlider)
            )

            AnimationTypes.CUBE_IN -> jSlider.setPageTransformer(true, CubeIn())


            AnimationTypes.CUBE_OUT -> jSlider.setPageTransformer(true, CubeOut())


            AnimationTypes.FLIP_HORIZONTAL -> jSlider.setPageTransformer(true, FlipHorizontal())


            AnimationTypes.FLIP_VERTICAL -> jSlider.setPageTransformer(true, FlipVertical())


            AnimationTypes.ROTATE_UP -> jSlider.setPageTransformer(true, RotateUp())


            AnimationTypes.ROTATE_DOWN -> jSlider.setPageTransformer(true, RotateDown())


            AnimationTypes.FOREGROUND_TO_BACKGROUND -> jSlider.setPageTransformer(
                true, ForegroundToBackground()
            )


            AnimationTypes.BACKGROUND_TO_FOREGROUND -> jSlider.setPageTransformer(
                true, BackgroundToForeground()
            )


            AnimationTypes.TOSS -> jSlider.setPageTransformer(true, Toss())


            AnimationTypes.GATE -> jSlider.setPageTransformer(true, Gate())


            AnimationTypes.FIDGET_SPINNER -> jSlider.setPageTransformer(true, FidgetSpinner())

            AnimationTypes.TABLET_SLIDE -> jSlider.setPageTransformer(true, TabletSlide())

        }
    }

    private fun exception() {
        if (jSlider.adapter != null) throw IllegalArgumentException("You cannot change anything on the Slider after setSlider() method call.")
    }

    private class Slider(context: Context) : ViewPager(context) {

        /**
         * Created by Jummania on 08,November,2023.
         * Email: sharifuddinjumman@gmail.com
         * Dhaka, Bangladesh.
         */

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


        fun setSlider(slider: Slide, dotIndicatorLayout: LinearLayout) {
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

            /**
             * Created by Jummania on 08,November,2023.
             * Email: sharifuddinjumman@gmail.com
             * Dhaka, Bangladesh.
             */

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

        private class JLayout(context: Context?) : LinearLayout(context) {

            /**
             * Created by Jummania on 08,November,2023.
             * Email: sharifuddinjumman@gmail.com
             * Dhaka, Bangladesh.
             */

            private val paint = Paint()

            init {
                paint.style = Paint.Style.FILL
            }

            override fun onDraw(canvas: Canvas) {
                canvas.drawCircle(
                    (width / 2).toFloat(),
                    height.toFloat() / 2,
                    (min(width, height) / 2).toFloat(),
                    paint
                )
            }

            fun setColor(color: Int) {
                paint.color = color
                invalidate() // Request a redraw to reflect the changes
            }
        }
    }
}