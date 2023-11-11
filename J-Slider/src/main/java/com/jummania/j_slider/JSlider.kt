package com.jummania.j_slider

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Scroller
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.SCROLL_STATE_DRAGGING
import com.jummania.j_slider.JSlider.BooleanObject.autoSlidingBoolean
import com.jummania.j_slider.JSlider.BooleanObject.indicatorBoolean
import com.jummania.j_slider.JSlider.BooleanObject.isDragging
import com.jummania.j_slider.JSlider.IntObject.defaultIndicatorColor
import com.jummania.j_slider.JSlider.IntObject.selectedIndicatorColor
import com.jummania.j_slider.JSlider.IntObject.size
import com.jummania.j_slider.JSlider.IntObject.slidingDuration
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
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
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

    private lateinit var listener: OnSlideChangeListener

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

        addView(jSlider)
        addView(dotIndicatorLayout)
    }

    fun setSlider(slider: Slide) {
        jSlider.apply {
            if (slider.count > 0) {
                adapter = slider

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

                if (autoSlidingBoolean) autoSlidingBoolean = sliders > 1


                update = Runnable {
                    if (!isDragging && autoSlidingBoolean) {
                        var currentPage: Int = currentItem
                        if (currentPage == sliders - 1) currentPage = 0
                        else currentPage += 1
                        setCurrentItem(currentPage, true)
                    }
                }

                addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                    override fun onPageScrolled(
                        position: Int, positionOffset: Float, positionOffsetPixels: Int
                    ) {
                        if (this@JSlider::listener.isInitialized) listener.onSliderScrolled(
                            position, positionOffset, positionOffsetPixels
                        )
                    }

                    override fun onPageSelected(position: Int) {

                        if (this@JSlider::listener.isInitialized) listener.onSliderSelected(position)
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

                        if (this@JSlider::listener.isInitialized) listener.onSliderScrollStateChanged(
                            state
                        )

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
        IntObject.slidingDuration = slidingDuration
    }

    fun getSlidingDuration(): Long = slidingDuration

    fun setIndicatorSize(size: Int) {
        exception()
        IntObject.size = size
    }

    fun getIndicatorSize(): Int = size

    fun setIndicatorColor(defaultColor: Int, selectedColor: Int) {
        exception()
        defaultIndicatorColor = defaultColor
        selectedIndicatorColor = selectedColor
    }

    fun getDefaultIndicatorColor(): Int = defaultIndicatorColor
    fun getSelectedIndicatorColor(): Int = selectedIndicatorColor

    fun enableIndicator(boolean: Boolean) {
        exception()
        indicatorBoolean = boolean
    }

    fun enableAutoSliding(boolean: Boolean) {
        exception()
        autoSlidingBoolean = boolean
    }

    fun setPageTransformer(boolean: Boolean, pageTransformer: ViewPager.PageTransformer) {
        jSlider.setPageTransformer(boolean, pageTransformer)
    }

    fun setSliderPadding(left: Int, top: Int, right: Int, bottom: Int) {
        jSlider.setPadding(left, top, right, bottom)
    }

    fun startSliding() {
        autoSlidingBoolean = true
        if (this@JSlider::update.isInitialized) {
            updateHandler.removeCallbacks(update)
            updateHandler.postDelayed(update, slidingDuration)
        } else if (jSlider.adapter == null) Log.d(
            this@JSlider.javaClass.simpleName,
            "After setting the slider it will start sliding automatically."
        )
    }

    fun stopSliding() {
        autoSlidingBoolean = false
        if (this@JSlider::update.isInitialized) updateHandler.removeCallbacks(update)
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

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        if (this@JSlider::update.isInitialized) {
            if (hasWindowFocus && autoSlidingBoolean) {
                updateHandler.removeCallbacks(update)
                updateHandler.postDelayed(update, slidingDuration)
            } else updateHandler.removeCallbacks(update)
        }
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

    private class Slider(context: Context) : ViewPager(context) {

        /**
         * Created by Jummania on 08,November,2023.
         * Email: sharifuddinjumman@gmail.com
         * Dhaka, Bangladesh.
         */

        override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
            val mode = MeasureSpec.getMode(measureSpec)
            if (mode == MeasureSpec.UNSPECIFIED || mode == MeasureSpec.AT_MOST) {
                val child = getChildAt(0)
                if (child == null) {
                    super.onMeasure(widthMeasureSpec, heightMeasureSpec)
                    return
                }
                child.measure(
                    widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
                )

                super.onMeasure(
                    widthMeasureSpec,
                    MeasureSpec.makeMeasureSpec(child.measuredHeight, MeasureSpec.EXACTLY)
                )
            } else super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureSpec = heightMeasureSpec
        val mode = MeasureSpec.getMode(measureSpec)
        if (mode == MeasureSpec.UNSPECIFIED || mode == MeasureSpec.AT_MOST) {
            val child = getChildAt(0)
            child.measure(
                widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
            )

            measureSpec = MeasureSpec.makeMeasureSpec(child.measuredHeight, MeasureSpec.EXACTLY)
        }
        super.onMeasure(widthMeasureSpec, measureSpec)
    }

    companion object {
        private var measureSpec: Int = 0
    }

    interface OnSlideChangeListener {
        fun onSliderScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int)
        fun onSliderSelected(position: Int)
        fun onSliderScrollStateChanged(state: Int)
    }

    fun addOnSlideChangeListener(listener: OnSlideChangeListener) {
        this@JSlider.listener = listener
    }
}