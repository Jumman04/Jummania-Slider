package com.jummania.j_slider

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
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

/**
 * Created by Jummania on 08,November,2023.
 * Email: sharifuddinjumman@gmail.com
 * Dhaka, Bangladesh.
 */

class JSlider @JvmOverloads constructor(
    context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {


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
}