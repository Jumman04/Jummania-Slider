package com.jummania

import android.content.Context
import android.content.res.Resources
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
import androidx.viewpager.widget.ViewPager.SCROLL_STATE_IDLE
import com.jummania.JSlider.BooleanObject.autoSlidingBoolean
import com.jummania.JSlider.BooleanObject.indicatorBoolean
import com.jummania.JSlider.BooleanObject.isDragging
import com.jummania.JSlider.BooleanObject.isSliding
import com.jummania.JSlider.IntObject.defaultIndicatorColor
import com.jummania.JSlider.IntObject.indicatorMarginHorizontal
import com.jummania.JSlider.IntObject.measureSpec
import com.jummania.JSlider.IntObject.selectedIndicatorColor
import com.jummania.JSlider.IntObject.size
import com.jummania.JSlider.IntObject.slidingDuration
import com.jummania.animations.AnimationTypes
import com.jummania.animations.BackgroundToForeground
import com.jummania.animations.CubeIn
import com.jummania.animations.CubeOut
import com.jummania.animations.DepthSlide
import com.jummania.animations.DepthSlide2
import com.jummania.animations.FidgetSpinner
import com.jummania.animations.FlipHorizontal
import com.jummania.animations.FlipVertical
import com.jummania.animations.ForegroundToBackground
import com.jummania.animations.Gate
import com.jummania.animations.RotateDown
import com.jummania.animations.RotateUp
import com.jummania.animations.TabletSlide
import com.jummania.animations.Toss
import com.jummania.animations.ZoomIn
import com.jummania.animations.ZoomOut
import kotlin.math.min

/**
 * Created by Jummania on 08,November,2023.
 * Email: sharifuddinjumman@gmail.com
 * Dhaka, Bangladesh.
 */

/**
 * Custom Slider class that provides additional features and customization options.
 * This class extends RelativeLayout and provides a customizable slider view
 * with indicator dots.
 *
 * @param context The context in which the Slider is created.
 * @param attrs The attributes set for the Slider.
 * @param defStyleAttr An attribute in the current theme that contains a reference to a style resource.
 */

class JSlider @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    /**
     * Created by Jummania on 08, November, 2023.
     * Email: sharifuddinjumman@gmail.com
     * Dhaka, Bangladesh.
     */

    // Lazy initialization of the Slider component
    private val jSlider: Slider by lazy {
        Slider(context)
    }

    // Lazy initialization of the selected dot indicator
    private val selectedDot by lazy {
        JLayout(context).apply {
            // Set the layout parameters for the selected dot indicator
            layoutParams = LayoutParams(
                size, size
            )
            (layoutParams as LayoutParams).addRule(ALIGN_PARENT_BOTTOM) // Align to the bottom of the parent view
            gravity = Gravity.CENTER // Center the selected dot indicator
            orientation = LinearLayout.HORIZONTAL // Set the orientation to horizontal
        }
    }

    // Lazy initialization of the dot indicator layout
    private val dotIndicatorLayout: LinearLayout by lazy {
        LinearLayout(context).apply {
            // Set the layout parameters for the dot indicator layout
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT
            )
            (layoutParams as LayoutParams).addRule(ALIGN_PARENT_BOTTOM) // Align to the bottom of the parent view
            gravity = Gravity.CENTER // Center the dot indicator layout
            orientation = LinearLayout.HORIZONTAL // Set the orientation to horizontal
        }
    }

    // Handler for updating the dot indicator position during auto-sliding
    val updateHandler by lazy {
        Handler(Looper.getMainLooper())
    }

    // Runnable object for updating the dot indicator position
    private lateinit var update: Runnable

    // Object to store integer values related to the slider configuration
    private object IntObject {
        var measureSpec: Int = 0 // Measure spec for the slider
        var slidingDuration: Long = 1555 // Sliding duration in milliseconds
        var size = 30 // Size of the dot indicator
        var indicatorMarginHorizontal = 6 // Horizontal margin between dot indicators
        var selectedIndicatorColor = Color.WHITE // Color of the selected dot indicator
        var defaultIndicatorColor =
            Color.parseColor("#80ffffff") // Default color of the dot indicators
    }

    // Object to store boolean values related to the slider behavior
    private object BooleanObject {
        var isDragging: Boolean = false // Indicates whether the slider is being dragged
        var indicatorBoolean = true // Enables or disables the dot indicator
        var autoSlidingBoolean = true // Enables or disables auto-sliding
        var isSliding = false // Indicates whether the slider is currently sliding
    }

    // Listener for slider change events
    private lateinit var listener: OnSlideChangeListener

    init {
        // Obtain styled attributes from XML
        val typedArray: TypedArray = context.theme.obtainStyledAttributes(
            attrs, R.styleable.JSlider, defStyleAttr, defStyleAttr
        )

        // Set sliding duration based on the attribute, default to 1555 milliseconds
        setSlidingDuration(
            typedArray.getInt(R.styleable.JSlider_slidingDuration, 1555).toLong()
        )

        // Set indicator size based on the attribute, default to 30 pixels
        setIndicatorSize(
            typedArray.getDimensionPixelSize(
                R.styleable.JSlider_indicatorSize, 30
            )
        )

        // Set padding for the slider based on left, top, right, and bottom attributes
        setSliderPadding(
            typedArray.getDimensionPixelSize(R.styleable.JSlider_sliderPaddingLeft, 0),
            typedArray.getDimensionPixelSize(R.styleable.JSlider_sliderPaddingTop, 0),
            typedArray.getDimensionPixelSize(R.styleable.JSlider_sliderPaddingRight, 0),
            typedArray.getDimensionPixelSize(R.styleable.JSlider_sliderPaddingBottom, 0)
        )

        // Enable or disable auto-sliding based on the attribute, default to true
        enableAutoSliding(typedArray.getBoolean(R.styleable.JSlider_enableAutoSliding, true))

        // Enable or disable the indicator based on the attribute, default to true
        enableIndicator(typedArray.getBoolean(R.styleable.JSlider_enableIndicator, true))

        // Set indicator colors based on the attributes, default to semi-transparent white and white
        setIndicatorColor(
            typedArray.getColor(
                R.styleable.JSlider_defaultIndicatorColor, Color.parseColor("#90ffffff")
            ), typedArray.getColor(R.styleable.JSlider_selectedIndicatorColor, Color.WHITE)
        )

        // Set padding for the indicator based on left, top, right, and bottom attributes
        indicatorPadding(
            0,
            0,
            0,
            typedArray.getDimensionPixelSize(R.styleable.JSlider_indicatorPaddingBottom, 55)
        )

        // Set horizontal margin for the indicator dots, default to 6 pixels
        setIndicatorMarginHorizontal(
            typedArray.getDimensionPixelSize(
                R.styleable.JSlider_indicatorMarginHorizontal, 6
            )
        )

        // Add the slider and indicator layout to the view
        addView(jSlider)
        addView(dotIndicatorLayout)
    }


    /**
     * Set up the JSlider with a DefaultSlider.
     *
     * @param slider The DefaultSlider to be set up.
     */
    fun setSlider(slider: DefaultSlider) {
        jSlider.apply {
            // Get the total number of sliders
            val sliders = slider.count

            // Check if there are sliders to display
            if (sliders > 0) {
                // Set the adapter to the provided DefaultSlider
                adapter = slider

                // Initialize a list to hold indicator dots
                val dots: MutableList<JLayout> by lazy { mutableListOf() }

                // Set up selected dot for the current position if indicator is enabled
                if (indicatorBoolean) {
                    selectedDot.setBackgroundResource(R.drawable.indicator)
                    selectedDot.setColor(selectedIndicatorColor)
                    selectedDot.layoutParams.width = size
                    selectedDot.layoutParams.height = size

                    val dotLayoutParams = LinearLayout.LayoutParams(size, size)
                    dotLayoutParams.marginStart = indicatorMarginHorizontal
                    dotLayoutParams.marginEnd = indicatorMarginHorizontal

                    val max =
                        (Resources.getSystem().displayMetrics.widthPixels / (size + indicatorMarginHorizontal * 2)) - 1

                    // Create indicator dots and add them to the layout
                    for (i in 0 until sliders) {
                        if (i == max) break
                        val dot = JLayout(context)
                        dot.layoutParams = dotLayoutParams
                        dot.setBackgroundResource(R.drawable.indicator)
                        dot.setColor(defaultIndicatorColor)
                        dotIndicatorLayout.addView(dot)
                        dots.add(dot)
                    }

                    // Set the initial position of the selected dot
                    if (dots.isNotEmpty()) {
                        selectedDot.post {
                            selectedDot.x = dots[0].x
                        }
                        this@JSlider.addView(selectedDot)
                    }
                }

                // Check if auto-sliding is enabled based on the number of sliders
                if (autoSlidingBoolean) autoSlidingBoolean = sliders > 1

                // Set up the auto-sliding update runnable
                update = Runnable {
                    if (!isDragging && autoSlidingBoolean) setCurrentItem(
                        if (currentItem == sliders - 1) 0 else currentItem + 1, true
                    )
                }

                val max = dots.size - 1
                // Add a listener to track page changes and update indicators
                addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                    override fun onPageScrolled(
                        i: Int, positionOffset: Float, positionOffsetPixels: Int
                    ) {
                        // Update the selected dot position if indicator is enabled
                        if (indicatorBoolean && dots.isNotEmpty()) {
                            val position = i % dots.size
                            if (position == max) {
                                val targetX = dots[0].x
                                selectedDot.x =
                                    targetX + (1 - positionOffset) * (dots[max].x - targetX)
                            } else {
                                val targetX = dots[position].x
                                selectedDot.x =
                                    targetX + positionOffset * ((if (position < max) dots[position + 1].x else targetX) - targetX)
                            }

                        }

                        // Notify the external listener about the page scroll event
                        if (this@JSlider::listener.isInitialized) listener.onSliderScrolled(
                            i, positionOffset, positionOffsetPixels
                        )
                    }

                    override fun onPageSelected(position: Int) {
                        // Handle auto-sliding when a new page is selected
                        if (autoSlidingBoolean) {
                            updateHandler.removeCallbacks(update)
                            updateHandler.postDelayed(update, slidingDuration)
                        }

                        // Notify the external listener about the page selection event
                        if (this@JSlider::listener.isInitialized) listener.onSliderSelected(position)
                    }

                    override fun onPageScrollStateChanged(state: Int) {
                        // Handle auto-sliding when dragging stops
                        if (isDragging && state != SCROLL_STATE_DRAGGING && autoSlidingBoolean) {
                            updateHandler.removeCallbacks(update)
                            updateHandler.postDelayed(update, slidingDuration)
                        }

                        // Update flags based on the scroll state
                        isDragging = state == SCROLL_STATE_DRAGGING
                        isSliding = state == SCROLL_STATE_IDLE

                        // Notify the external listener about the scroll state change
                        if (this@JSlider::listener.isInitialized) listener.onSliderScrollStateChanged(
                            state
                        )
                    }
                })

                // Set a custom scroller for smoother scrolling
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


    /**
     * Set up the JSlider with an InfinitySlider.
     *
     * @param slider The InfinitySlider to be set up.
     */
    fun setSlider(slider: InfinitySlider) {
        jSlider.apply {
            // Get the total number of sliders from the InfinitySlider
            val sliders = slider.itemCount()

            // Check if there are sliders to display
            if (sliders > 0) {
                // Set the adapter to the provided InfinitySlider
                adapter = slider

                // Initialize a list to hold indicator dots
                val dots: MutableList<JLayout> by lazy { mutableListOf() }

                // Set up indicator dots if indicator is enabled
                if (indicatorBoolean) {
                    selectedDot.setBackgroundResource(R.drawable.indicator)
                    selectedDot.setColor(selectedIndicatorColor)
                    selectedDot.layoutParams.width = size
                    selectedDot.layoutParams.height = size

                    val dotLayoutParams = LinearLayout.LayoutParams(size, size)
                    dotLayoutParams.marginStart = indicatorMarginHorizontal
                    dotLayoutParams.marginEnd = indicatorMarginHorizontal


                    val max =
                        (Resources.getSystem().displayMetrics.widthPixels / (size + indicatorMarginHorizontal * 2)) - 1

                    // Create indicator dots and add them to the layout
                    for (i in 0 until sliders) {
                        if (i == max) break
                        val dot = JLayout(context)
                        dot.layoutParams = dotLayoutParams
                        dot.setBackgroundResource(R.drawable.indicator)
                        dot.setColor(defaultIndicatorColor)
                        dotIndicatorLayout.addView(dot)
                        dots.add(dot)
                    }

                    // Set the color of the first indicator dot to the selected color
                    if (dots.isNotEmpty()) {
                        selectedDot.post {
                            selectedDot.x = dots[0].x
                        }
                        this@JSlider.addView(selectedDot)
                    }
                }

                // Check if auto-sliding is enabled based on the number of sliders
                if (autoSlidingBoolean) autoSlidingBoolean = sliders > 1

                // Set up the auto-sliding update runnable
                update = Runnable {
                    if (!isDragging && autoSlidingBoolean) setCurrentItem(
                        (currentItem % slider.count) + 1, true
                    )
                }

                val max = dots.size - 1
                // Add a listener to track page changes and update indicators
                addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                    override fun onPageScrolled(
                        i: Int, positionOffset: Float, positionOffsetPixels: Int
                    ) {
                        // Update the selected dot position if indicator is enabled
                        if (indicatorBoolean && dots.isNotEmpty()) {
                            val position = i % dots.size
                            if (position == max) {
                                val targetX = dots[0].x
                                selectedDot.x =
                                    targetX + (1 - positionOffset) * (dots[max].x - targetX)
                            } else {
                                val targetX = dots[position].x
                                selectedDot.x =
                                    targetX + positionOffset * ((if (position < max) dots[position + 1].x else targetX) - targetX)
                            }

                        }
                        // Notify the external listener about the page scroll event
                        if (this@JSlider::listener.isInitialized) listener.onSliderScrolled(
                            i, positionOffset, positionOffsetPixels
                        )
                    }

                    override fun onPageSelected(position: Int) {

                        // Handle auto-sliding when a new page is selected
                        if (autoSlidingBoolean) {
                            updateHandler.removeCallbacks(update)
                            updateHandler.postDelayed(update, slidingDuration)
                        }

                        // Notify the external listener about the page selection event
                        if (this@JSlider::listener.isInitialized) listener.onSliderSelected(position)
                    }

                    override fun onPageScrollStateChanged(state: Int) {
                        // Handle auto-sliding when dragging stops
                        if (isDragging && state != SCROLL_STATE_DRAGGING && autoSlidingBoolean) {
                            updateHandler.removeCallbacks(update)
                            updateHandler.postDelayed(update, slidingDuration)
                        }

                        // Update flags based on the scroll state
                        isDragging = state == SCROLL_STATE_DRAGGING
                        isSliding = state == SCROLL_STATE_IDLE

                        // Notify the external listener about the scroll state change
                        if (this@JSlider::listener.isInitialized) listener.onSliderScrollStateChanged(
                            state
                        )
                    }
                })

                // Set a custom scroller for smoother scrolling
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


    /**
     * Abstract class for a default slider.
     *
     * @see PagerAdapter
     */
    abstract class DefaultSlider : PagerAdapter() {

        /**
         * Created by Jummania on 08, November, 2023.
         * Email: sharifuddinjumman@gmail.com
         * Dhaka, Bangladesh.
         */

        /**
         * Abstract method to get the view for a slider item.
         *
         * @param layoutInflater The layout inflater to inflate the view.
         * @param parent The parent view group.
         * @return The inflated view for the slider item.
         */
        abstract fun getView(layoutInflater: LayoutInflater, parent: ViewGroup): View

        /**
         * Abstract method called when a slider item is created.
         *
         * @param view The view representing the slider item.
         * @param position The position of the slider item.
         */
        abstract fun onSliderCreate(view: View, position: Int)

        /**
         * Check if a given view is associated with a specific object.
         *
         * @param view The view to check.
         * @param `object` The object to compare with.
         * @return True if the view is associated with the given object, false otherwise.
         */
        override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

        /**
         * Instantiate a slider item at the specified position.
         *
         * @param parent The parent view group.
         * @param position The position of the slider item.
         * @return The instantiated view for the slider item.
         */
        override fun instantiateItem(parent: ViewGroup, position: Int): View {
            // Inflate the view for the slider item
            val view = getView(LayoutInflater.from(parent.context), parent)
            // Callback method when a slider item is created
            onSliderCreate(view, position)
            // Add the view to the parent view group
            parent.addView(view)
            return view
        }

        /**
         * Remove a slider item from the specified container.
         *
         * @param container The parent view group.
         * @param position The position of the slider item to be removed.
         * @param `object` The object associated with the slider item.
         */
        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            // Remove the view associated with the slider item from the parent view group
            container.removeView(`object` as View)
        }
    }


    /**
     * Abstract class for an infinite slider with a dynamic number of items.
     *
     * @see PagerAdapter
     */
    abstract class InfinitySlider : PagerAdapter() {

        /**
         * Created by Jummania on 08, November, 2023.
         * Email: sharifuddinjumman@gmail.com
         * Dhaka, Bangladesh.
         */

        /**
         * Abstract method to get the total number of items in the slider.
         *
         * @return The total number of items.
         */
        abstract fun itemCount(): Int

        /**
         * Return the total number of items in the slider (virtually infinite).
         *
         * @return The maximum possible value of an integer, representing an infinite number of items.
         */
        override fun getCount(): Int = Int.MAX_VALUE

        /**
         * Abstract method to get the view for a slider item.
         *
         * @param layoutInflater The layout inflater to inflate the view.
         * @param parent The parent view group.
         * @return The inflated view for the slider item.
         */
        abstract fun getView(layoutInflater: LayoutInflater, parent: ViewGroup): View

        /**
         * Abstract method called when a slider item is created.
         *
         * @param view The view representing the slider item.
         * @param position The position of the slider item.
         */
        abstract fun onSliderCreate(view: View, position: Int)

        /**
         * Check if a given view is associated with a specific object.
         *
         * @param view The view to check.
         * @param `object` The object to compare with.
         * @return True if the view is associated with the given object, false otherwise.
         */
        override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

        /**
         * Instantiate a slider item at the specified position.
         *
         * @param parent The parent view group.
         * @param position The position of the slider item.
         * @return The instantiated view for the slider item.
         */
        override fun instantiateItem(parent: ViewGroup, position: Int): View {
            // Inflate the view for the slider item
            val view = getView(LayoutInflater.from(parent.context), parent)
            // Callback method when a slider item is created, using modulo to ensure the position is within the item count
            onSliderCreate(view, position % itemCount())
            // Add the view to the parent view group
            parent.addView(view)
            return view
        }

        /**
         * Remove a slider item from the specified container.
         *
         * @param container The parent view group.
         * @param position The position of the slider item to be removed.
         * @param `object` The object associated with the slider item.
         */
        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            // Remove the view associated with the slider item from the parent view group
            container.removeView(`object` as View)
        }
    }


    /**
     * Set the sliding duration for transitioning between slides.
     *
     * @param slidingDuration The duration in milliseconds for each slide transition.
     */
    fun setSlidingDuration(slidingDuration: Long) {
        exception()
        IntObject.slidingDuration = slidingDuration
    }

    /**
     * Get the current sliding duration.
     *
     * @return The current sliding duration in milliseconds.
     */
    fun getSlidingDuration(): Long = slidingDuration

    /**
     * Set the size of the indicator dots.
     *
     * @param size The size of the indicator dots.
     */
    fun setIndicatorSize(size: Int) {
        exception()
        IntObject.size = size
    }

    /**
     * Get the size of the indicator dots.
     *
     * @return The size of the indicator dots.
     */
    fun getIndicatorSize(): Int = size

    /**
     * Set the colors for the default and selected indicator dots.
     *
     * @param defaultColor The color of the default indicator dot.
     * @param selectedColor The color of the selected indicator dot.
     */
    fun setIndicatorColor(defaultColor: Int, selectedColor: Int) {
        exception()
        defaultIndicatorColor = defaultColor
        selectedIndicatorColor = selectedColor
    }

    /**
     * Get the color of the default indicator dot.
     *
     * @return The color of the default indicator dot.
     */
    fun getDefaultIndicatorColor(): Int = defaultIndicatorColor

    /**
     * Get the color of the selected indicator dot.
     *
     * @return The color of the selected indicator dot.
     */
    fun getSelectedIndicatorColor(): Int = selectedIndicatorColor

    /**
     * Enable or disable the indicator dots.
     *
     * @param boolean True to enable the indicator dots, false to disable them.
     */
    fun enableIndicator(boolean: Boolean) {
        exception()
        indicatorBoolean = boolean
    }

    /**
     * Enable or disable auto-sliding between slides.
     *
     * @param boolean True to enable auto-sliding, false to disable it.
     */
    fun enableAutoSliding(boolean: Boolean) {
        exception()
        autoSlidingBoolean = boolean
    }

    /**
     * Set a custom page transformer for the Slider.
     *
     * @param boolean True to enable the page transformer, false to disable it.
     * @param pageTransformer The custom page transformer to be applied.
     */
    fun setPageTransformer(boolean: Boolean, pageTransformer: ViewPager.PageTransformer) {
        jSlider.setPageTransformer(boolean, pageTransformer)
    }

    /**
     * Set padding for the Slider.
     *
     * @param left The left padding in pixels.
     * @param top The top padding in pixels.
     * @param right The right padding in pixels.
     * @param bottom The bottom padding in pixels.
     */
    fun setSliderPadding(left: Int, top: Int, right: Int, bottom: Int) {
        jSlider.setPadding(left, top, right, bottom)
    }

    /**
     * Set horizontal margin for the indicator dots.
     *
     * @param marginHorizontal The horizontal margin in pixels.
     */
    fun setIndicatorMarginHorizontal(marginHorizontal: Int) {
        exception()
        indicatorMarginHorizontal = marginHorizontal
    }

    /**
     * Start auto-sliding with the specified interval.
     */
    fun startAutoSliding() {
        autoSlidingBoolean = true
        if (this@JSlider::update.isInitialized) {
            updateHandler.removeCallbacks(update)
            updateHandler.postDelayed(update, slidingDuration)
        } else if (jSlider.adapter == null) Log.d(
            this@JSlider.javaClass.simpleName,
            "After setting the slider it will start sliding automatically."
        )
    }

    /**
     * Stop auto-sliding.
     */
    fun stopAutoSliding() {
        autoSlidingBoolean = false
        if (this@JSlider::update.isInitialized) updateHandler.removeCallbacks(update)
    }

    /**
     * Slide to the next page.
     */
    fun slideNext() {
        val slider = jSlider.adapter
        if (slider is DefaultSlider) jSlider.setCurrentItem(
            if (jSlider.currentItem == slider.count - 1) 0 else jSlider.currentItem + 1, true
        )
        else if (slider is InfinitySlider) jSlider.setCurrentItem(
            (jSlider.currentItem % slider.count) + 1, true
        )
    }

    /**
     * Slide to the previous page.
     */
    fun slidePrevious() {
        val slider = jSlider.adapter
        if (slider is DefaultSlider) jSlider.setCurrentItem(
            if (jSlider.currentItem == 0) slider.count - 1 else jSlider.currentItem - 1, true
        )
        else if (slider is InfinitySlider) jSlider.setCurrentItem(
            (jSlider.currentItem % slider.count) - 1, true
        )
    }

    /**
     * Check if the Slider is currently sliding.
     *
     * @return false if sliding, True otherwise.
     */
    fun isSliding(): Boolean = !isSliding

    /**
     * Set padding for the indicator dots.
     *
     * @param left The left padding in pixels.
     * @param top The top padding in pixels.
     * @param right The right padding in pixels.
     * @param bottom The bottom padding in pixels.
     */
    fun indicatorPadding(left: Int, top: Int, right: Int, bottom: Int) {
        val layoutParams = (selectedDot.layoutParams as? MarginLayoutParams)
        layoutParams?.setMargins(left, top, right, bottom)
        dotIndicatorLayout.setPadding(left, top, right, bottom)
    }

    /**
     * Set a specific slide animation for the ViewPager.
     *
     * @param animationType The type of animation to be applied.
     */
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


    /**
     * Check if the Slider has been set, and throw an exception if any modification is attempted
     * after the setSlider() method call.
     */
    private fun exception() {
        if (jSlider.adapter != null) throw IllegalArgumentException("You cannot change anything on the Slider after setSlider() method call.")
    }


    /**
     * Custom Scroller class that overrides the startScroll method to use a custom sliding duration.
     *
     * @param context The context in which the Scroller is created.
     */
    private inner class MyScroller(context: Context?) : Scroller(context) {

        /**
         * Override the startScroll method to set a custom sliding duration.
         *
         * @param startX Starting X coordinate of the scroll.
         * @param startY Starting Y coordinate of the scroll.
         * @param dx Horizontal distance to travel.
         * @param dy Vertical distance to travel.
         * @param duration Duration of the scroll in milliseconds.
         */
        override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
            super.startScroll(startX, startY, dx, dy, slidingDuration.toInt())
        }
    }


    /**
     * Called when the window hosting the view gains or loses focus.
     *
     * @param hasWindowFocus True if the window hosting the view has focus, false otherwise.
     */
    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        if (this@JSlider::update.isInitialized) {
            // Check if the update Runnable is initialized
            if (hasWindowFocus && autoSlidingBoolean) {
                // If the window gains focus and auto-sliding is enabled, start the auto-sliding
                updateHandler.removeCallbacks(update)
                updateHandler.postDelayed(update, slidingDuration)
            } else {
                // If the window loses focus or auto-sliding is disabled, stop the auto-sliding
                updateHandler.removeCallbacks(update)
            }
        }
        super.onWindowFocusChanged(hasWindowFocus)
    }


    /**
     * Custom LinearLayout that draws a colored circle.
     *
     * @param context The context in which the JLayout is created.
     */
    private class JLayout(context: Context?) : LinearLayout(context) {

        // Paint object used for drawing
        private val paint = Paint()

        init {
            // Initialize the paint style
            paint.style = Paint.Style.FILL
        }

        /**
         * Override the onDraw method to draw a colored circle.
         *
         * @param canvas The canvas on which to draw the circle.
         */
        override fun onDraw(canvas: Canvas) {
            // Draw a colored circle in the center of the layout
            canvas.drawCircle(
                (width / 2).toFloat(),
                height.toFloat() / 2,
                (min(width, height) / 2).toFloat(),
                paint
            )
        }

        /**
         * Set the color of the circle.
         *
         * @param color The color to set for the circle.
         */
        fun setColor(color: Int) {
            paint.color = color
            invalidate() //Invalid and force to reDraw
        }
    }


    /**
     * Custom ViewPager with a modified onMeasure method.
     *
     * @param context The context in which the Slider is created.
     */
    private class Slider(context: Context) : ViewPager(context) {

        /**
         * Override the onMeasure method to adjust the height of the ViewPager.
         *
         * @param widthMeasureSpec Width measurement specification.
         * @param heightMeasureSpec Height measurement specification.
         */
        override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
            // Get the measurement mode for height
            val mode = MeasureSpec.getMode(measureSpec)

            // Check if the height mode is UNSPECIFIED or AT_MOST
            if (mode == MeasureSpec.UNSPECIFIED || mode == MeasureSpec.AT_MOST) {
                // Get the first child of the ViewPager
                val child = getChildAt(0)

                // Check if the child is null
                if (child == null) {
                    super.onMeasure(widthMeasureSpec, heightMeasureSpec)
                    return
                }

                // Measure the child with an unspecified height
                child.measure(
                    widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
                )

                // Set the height of the ViewPager based on the measured height of the child
                super.onMeasure(
                    widthMeasureSpec,
                    MeasureSpec.makeMeasureSpec(child.measuredHeight, MeasureSpec.EXACTLY)
                )
            } else {
                // If the height mode is EXACTLY or unspecified, use the original onMeasure
                super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            }
        }
    }


    /**
     * Override the onMeasure method to adjust the height of the ViewPager.
     *
     * @param widthMeasureSpec Width measurement specification.
     * @param heightMeasureSpec Height measurement specification.
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // Store the original heightMeasureSpec in a variable for future reference
        measureSpec = heightMeasureSpec

        // Get the measurement mode for height
        val mode = MeasureSpec.getMode(measureSpec)

        // Check if the height mode is UNSPECIFIED or AT_MOST
        if (mode == MeasureSpec.UNSPECIFIED || mode == MeasureSpec.AT_MOST) {
            // Get the first child of the ViewPager
            val child = getChildAt(0)

            // Check if the child is null
            if (child != null) {
                // Measure the child with an unspecified height
                child.measure(
                    widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
                )

                // Update the measureSpec with an exact height based on the measured height of the child
                measureSpec = MeasureSpec.makeMeasureSpec(child.measuredHeight, MeasureSpec.EXACTLY)
            }
        }

        // Call the original onMeasure method with the updated measureSpec
        super.onMeasure(widthMeasureSpec, measureSpec)
    }


    /**
     * Interface to define callbacks for slide change events in the JSlider.
     */
    interface OnSlideChangeListener {
        /**
         * Called when the slider is being scrolled.
         *
         * @param position The current position of the slider.
         * @param positionOffset The fractional offset of the slider's position.
         * @param positionOffsetPixels The offset of the slider's position in pixels.
         */
        fun onSliderScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int)

        /**
         * Called when a new slider position is selected.
         *
         * @param position The newly selected position of the slider.
         */
        fun onSliderSelected(position: Int)

        /**
         * Called when the scroll state of the slider changes.
         *
         * @param state The new scroll state. It can be SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, or SCROLL_STATE_SETTLING.
         */
        fun onSliderScrollStateChanged(state: Int)
    }

    /**
     * Adds an [OnSlideChangeListener] to the JSlider to receive slide change callbacks.
     *
     * @param listener The listener to be added.
     */
    fun addOnSlideChangeListener(listener: OnSlideChangeListener) {
        // Set the provided listener as the listener for slide change events in the JSlider
        this@JSlider.listener = listener
    }

}