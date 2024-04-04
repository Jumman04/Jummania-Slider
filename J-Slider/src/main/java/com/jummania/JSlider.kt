package com.jummania

import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
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
import com.jummania.JSlider.IntObject.indicatorUpdateType
import com.jummania.JSlider.IntObject.rules
import com.jummania.JSlider.IntObject.selectedIndicatorColor
import com.jummania.JSlider.IntObject.size
import com.jummania.JSlider.IntObject.slidingDuration
import com.jummania.types.Alignment
import com.jummania.types.AnimationTypes
import com.jummania.types.ShapeTypes
import com.jummania.types.UpdateTypes
import com.jummania.widgets.JScroller


/**
 *  * Created by Jummania on 08,November,2023.
 *  * Email: sharifuddinjumman@gmail.com
 *  * Dhaka, Bangladesh.
 *
 *
 * [JSlider] class that provides additional features and customization options.
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

    // Class implementation...

    // Lazy initialization of the Slider component
    private val jSlider: Slider by lazy {
        Slider(context)
    }

    // Lazy initialization of the dot indicator layout
    private val dotIndicatorLayout: LinearLayout by lazy {
        LinearLayout(context).apply {
            // Set the layout parameters for the dot indicator layout
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT
            )
            orientation = LinearLayout.HORIZONTAL // Set the orientation to horizontal
        }
    }

    // Lazy initialization of the dot indicator layout
    private val selectedIndicatorLayout: LinearLayout by lazy {
        LinearLayout(context).apply {
            // Set the layout parameters for the dot indicator layout
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT
            )
            orientation = LinearLayout.HORIZONTAL // Set the orientation to horizontal
        }
    }

    // Handler for updating the dot indicator position during auto-sliding
    private val updateHandler by lazy {
        Handler(Looper.getMainLooper())
    }

    // Runnable object for updating the dot indicator position
    private lateinit var update: Runnable

    /**
     * Represents the types of shapes that can be used for indicators.
     * The shapes are defined in the [ShapeTypes] enum class.
     */
    private lateinit var shapeTypes: ShapeTypes


    // Object to store integer values related to the slider configuration
    private object IntObject {
        var slidingDuration: Long = 1555 // Sliding duration in milliseconds
        var size = 33 // Size of the dot indicator
        var indicatorMarginHorizontal = 4 // Horizontal margin between dot indicators
        var selectedIndicatorColor = Color.WHITE // Color of the selected dot indicator
        var defaultIndicatorColor =
            Color.parseColor("#80ffffff") // Default color of the dot indicators

        // Type of indicator update
        var indicatorUpdateType: Int = 0

        // List of RelativeLayout rules for positioning the dot indicator
        val rules: List<Int> by lazy {
            listOf(
                ALIGN_PARENT_LEFT,
                ALIGN_PARENT_TOP,
                ALIGN_PARENT_RIGHT,
                ALIGN_PARENT_BOTTOM,
                CENTER_IN_PARENT,
                CENTER_HORIZONTAL,
                CENTER_VERTICAL,
                ALIGN_PARENT_START,
                ALIGN_PARENT_END
            )
        }

    }

    // Object to store boolean values related to the slider behavior
    private object BooleanObject {
        var isDragging: Boolean = false // Indicates whether the slider is being dragged
        var indicatorBoolean = true // Enables or disables the dot indicator
        var autoSlidingBoolean = true // Enables or disables auto-sliding
        var isSliding = false // Indicates whether the slider is currently sliding
    }

    // Listener for slider change events
    private var listener: OnSlideChangeListener? = null

    init {

        try {
            // Obtain styled attributes from XML
            val typedArray: TypedArray = context.theme.obtainStyledAttributes(
                attrs, R.styleable.JSlider, defStyleAttr, defStyleAttr
            )

            try {

                // Set sliding duration based on the attribute, default to 1555 milliseconds
                setSlidingDuration(
                    typedArray.getInt(R.styleable.JSlider_slidingDuration, 1555).toLong()
                )

                // Set indicator size based on the attribute, default to 30 pixels
                setIndicatorSize(
                    typedArray.getDimensionPixelSize(
                        R.styleable.JSlider_indicatorSize, 33
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
                enableAutoSliding(
                    typedArray.getBoolean(
                        R.styleable.JSlider_enableAutoSliding, true
                    )
                )

                // Enable or disable the indicator based on the attribute, default to true
                enableIndicator(typedArray.getBoolean(R.styleable.JSlider_enableIndicator, true))

                // Set indicator colors based on the attributes, default to semi-transparent white and white
                setIndicatorColor(
                    typedArray.getColor(
                        R.styleable.JSlider_defaultIndicatorColor, Color.parseColor("#90ffffff")
                    ), typedArray.getColor(R.styleable.JSlider_selectedIndicatorColor, Color.WHITE)
                )

                // Set padding for the indicator based on left, top, right, and bottom attributes
                setIndicatorPadding(
                    typedArray.getDimensionPixelSize(R.styleable.JSlider_indicatorPaddingLeft, 0),
                    typedArray.getDimensionPixelSize(R.styleable.JSlider_indicatorPaddingTop, 0),
                    typedArray.getDimensionPixelSize(R.styleable.JSlider_indicatorPaddingRight, 0),
                    typedArray.getDimensionPixelSize(R.styleable.JSlider_indicatorPaddingBottom, 55)
                )

                // Set horizontal margin for the indicator dots, default to 6 pixels
                setIndicatorMarginHorizontal(
                    typedArray.getDimensionPixelSize(
                        R.styleable.JSlider_indicatorMarginHorizontal, 4
                    )
                )

                // Set slide animation based on the specified attribute
                setSlideAnimation(
                    AnimationTypes.entries[typedArray.getInt(
                        R.styleable.JSlider_slideAnimation, AnimationTypes.DEFAULT.ordinal
                    )]
                )

                // Set indicator alignment based on the specified attribute
                setIndicatorAlignment(
                    Alignment.entries[typedArray.getInt(
                        R.styleable.JSlider_indicatorAlignment, Alignment.BOTTOM.ordinal
                    )]
                )
                // Set indicator gravity based on the specified attribute
                setIndicatorGravity(
                    typedArray.getInt(
                        R.styleable.JSlider_indicatorGravity, Gravity.CENTER
                    )
                )

                // Set indicator update mode based on the specified attribute
                setIndicatorUpdateTypes(
                    UpdateTypes.entries[typedArray.getInt(
                        R.styleable.JSlider_indicatorUpdateMode, UpdateTypes.SYNC.ordinal
                    )]
                )

                // Setting the indicator shape type for a JSlider.
                // This assumes that there is an enum called ShapeTypes defined
                // in the JSlider class or its associated class, which represents the available shape types.

                // Obtain a reference to the ShapeTypes enum and set the selected shape type based on the attributes
                // specified in the XML layout using the R.styleable.JSlider_indicatorShapeTypes attribute.
                // If the attribute is not defined, default to ShapeTypes.CIRCLE.
                setIndicatorShapeTypes(
                    ShapeTypes.entries[typedArray.getInt(
                        R.styleable.JSlider_indicatorShapeTypes, ShapeTypes.CIRCLE.ordinal
                    )]
                )


                // Sets the manual sliding behavior based on the value of the custom attribute "manualSlidable" defined
                // in the XML layout file using a TypedArray.

                setManualSlidable(typedArray.getBoolean(R.styleable.JSlider_manualSlidable, true))

            } finally {
                // Recycle the TypedArray to avoid memory leaks
                typedArray.recycle()
            }

            // Add the slider and indicator layout to the view
            addView(jSlider)
            addView(dotIndicatorLayout)
            addView(selectedIndicatorLayout)

        } catch (e: Exception) {
            e.printStackTrace()
        }

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
                val autoSliding = autoSlidingBoolean

                if (onSliderSet(sliders, autoSliding)) {
                    // Set up the auto-sliding update runnable
                    update = Runnable {
                        if (!isDragging && autoSliding) setCurrentItem(
                            if (currentItem == sliders - 1) 0 else currentItem + 1, true
                        )
                    }

                    // Handle auto-sliding
                    if (autoSliding) {
                        updateHandler.removeCallbacks(update)
                        updateHandler.postDelayed(update, slidingDuration)
                    }
                }

            }
        }
    }

    /**
     * Sets up the JSlider with indicator dots and other configurations.
     *
     * @param sliders get SliderItemCount.
     */
    private fun onSliderSet(sliders: Int, autoSliding: Boolean): Boolean {

        if (selectedIndicatorLayout.childCount == 0 && dotIndicatorLayout.childCount == 0) {

            // Initialize a list to hold indicator dots
            val dots: MutableList<JIndicator> by lazy { mutableListOf() }
            // Lazy initialization of the selected dot indicator

            val selectedDot by lazy {
                JIndicator(context, shapeTypes).apply {
                    // Set the layout parameters for the selected dot indicator
                    layoutParams = LayoutParams(
                        size, size
                    )
                    (layoutParams as LayoutParams).addRule(ALIGN_PARENT_BOTTOM) // Align to the bottom of the parent view
                    gravity = Gravity.CENTER // Center the selected dot indicator
                    setBackgroundResource(R.drawable.indicator)
                    setColor(selectedIndicatorColor)
                }
            }

            // Set up dot for the current position if indicator is enabled
            // Check if indicator dots should be displayed
            if (indicatorBoolean) {

                // Create layout parameters for the indicator dots
                val dotLayoutParams = LinearLayout.LayoutParams(size, size)
                dotLayoutParams.marginStart = indicatorMarginHorizontal
                dotLayoutParams.marginEnd = indicatorMarginHorizontal

                val padding =
                    (jSlider.paddingStart + jSlider.paddingEnd) + (paddingStart + paddingEnd) / 10

                // Calculate the maximum number of dots that can fit on the screen
                val max =
                    (Resources.getSystem().displayMetrics.widthPixels / (size + padding + indicatorMarginHorizontal * 2)) - 1

                // Create indicator dots and add them to the layout
                if (dotIndicatorLayout.childCount == 0) for (i in 0 until sliders) {
                    if (i == max) break

                    // Create a new JIndicator instance for the indicator dot
                    val dot = JIndicator(context, shapeTypes)

                    // Set layout parameters for the indicator dot
                    dot.layoutParams = dotLayoutParams

                    // Set a background resource for the indicator dot (if needed)
                    dot.setBackgroundResource(R.drawable.indicator)

                    // Set the default color for the indicator dot
                    dot.setColor(defaultIndicatorColor)

                    // Set padding for the indicator dot
                    dot.addPadding()

                    // Add the indicator dot to the dotIndicatorLayout
                    dotIndicatorLayout.addView(dot)

                    // Add the indicator dot to the list for future reference
                    dots.add(dot)
                }

                // Set the initial position of the selected dot
                if (dots.isNotEmpty()) {
                    selectedDot.post {
                        selectedDot.x = dots[0].x
                        selectedDot.y = dots[0].y
                    }

                    selectedIndicatorLayout.addView(selectedDot)
                }
            }


            val max = dots.size - 1
            var position: Int
            var targetX: Float
            // Add a listener to track page changes and update indicators
            jSlider.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(
                    i: Int, positionOffset: Float, positionOffsetPixels: Int
                ) {
                    // Update the selected dot position if indicator is enabled
                    if (indicatorBoolean && dots.isNotEmpty() && indicatorUpdateType == 0) {
                        position = i % dots.size

                        // Calculate the selected dot's X position based on the current position and offset
                        if (position == max) {
                            targetX = dots[0].x
                            selectedDot.x = targetX + (1 - positionOffset) * (dots[max].x - targetX)
                        } else {
                            targetX = dots[position].x
                            selectedDot.x =
                                targetX + positionOffset * ((if (position < max) dots[position + 1].x else targetX) - targetX)
                        }

                    }

                    // Notify the external listener about the page scroll event
                    listener?.onSliderScrolled(
                        i, positionOffset, positionOffsetPixels
                    )
                }

                override fun onPageSelected(i: Int) {

                    // Update the selected dot position if the indicator is enabled and the update type is not zero
                    if (indicatorBoolean && dots.isNotEmpty() && indicatorUpdateType != 0) {
                        position = i % dots.size

                        // Check the indicator update type and update the selected dot's X position accordingly
                        when (indicatorUpdateType) {
                            1 -> selectedDot.x = dots[position].x // Instantly set the X position
                            2 ->
                                // Animate the transition to the new X position
                                selectedDot.animate().x(dots[position].x).start()

                            // Add more cases if additional update types are introduced in the future
                        }
                    }


                    // Handle auto-sliding when a new page is selected
                    if (autoSliding) {
                        updateHandler.removeCallbacks(update)
                        updateHandler.postDelayed(update, slidingDuration)
                    }

                    // Notify the external listener about the page selection event
                    listener?.onSliderSelected(i)
                }

                override fun onPageScrollStateChanged(state: Int) {
                    // Handle auto-sliding when dragging stops
                    if (isDragging && state != SCROLL_STATE_DRAGGING && autoSliding) {
                        updateHandler.removeCallbacks(update)
                        updateHandler.postDelayed(update, slidingDuration)
                    }

                    // Update flags based on the scroll state
                    isDragging = state == SCROLL_STATE_DRAGGING
                    isSliding = state == SCROLL_STATE_IDLE

                    // Notify the external listener about the scroll state change
                    listener?.onSliderScrollStateChanged(
                        state
                    )
                }
            })

            // Set a custom scroller for smoother scrolling
            try {
                val viewPagerScroller = ViewPager::class.java.getDeclaredField("mScroller")
                viewPagerScroller.isAccessible = true
                viewPagerScroller[jSlider] = JScroller(context, slidingDuration.toInt())
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return true
        }

        return false
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

                val autoSliding = autoSlidingBoolean
                if (onSliderSet(sliders, autoSliding)) {

                    // Set up the auto-sliding update runnable
                    update = Runnable {
                        if (!isDragging && autoSliding) setCurrentItem(
                            (currentItem % slider.count) + 1, true
                        )
                    }

                    // Handle auto-sliding
                    if (autoSliding) {
                        updateHandler.removeCallbacks(update)
                        updateHandler.postDelayed(update, slidingDuration)
                    }
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
        }
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
    fun setIndicatorPadding(left: Int, top: Int, right: Int, bottom: Int) {
        selectedIndicatorLayout.setPadding(left, top, right, bottom)
        dotIndicatorLayout.setPadding(left, top, right, bottom)
    }

    /**
     * Sets the gravity for both the dot and selected indicators in the JSlider.
     * Gravity determines how the indicators are positioned within their respective layouts.
     *
     * @param gravity The gravity value to set. Should be one of the Gravity constants (e.g., Gravity.CENTER).
     */
    fun setIndicatorGravity(gravity: Int) {
        // Set the gravity for both dot and selected indicators
        dotIndicatorLayout.gravity = gravity
        selectedIndicatorLayout.gravity = gravity
    }

    /**
     * Sets the alignment for both the dot and selected indicators in the JSlider.
     * Alignment determines the positioning of the indicators within their respective layouts.
     *
     * @param alignment The alignment type to set. Should be one of the values defined in the Alignment enum class.
     */
    fun setIndicatorAlignment(alignment: Alignment) {
        // Retrieve layout params for both dot and selected indicators
        val dotLayoutParams = dotIndicatorLayout.layoutParams as LayoutParams
        val selectedDotLayoutParams = selectedIndicatorLayout.layoutParams as LayoutParams

        // Remove existing alignment rules
        for (rule in rules) {
            dotLayoutParams.removeRule(rule)
            selectedDotLayoutParams.removeRule(rule)
        }

        // Set the new alignment for both dot and selected indicators
        val gravity = alignment.alignment
        dotLayoutParams.addRule(gravity)
        selectedDotLayoutParams.addRule(gravity)
    }


    /**
     * Set a specific slide animation for the ViewPager.
     *
     * @param animationType The type of animation to be applied.
     */
    fun setSlideAnimation(animationType: AnimationTypes) {
        jSlider.setPageTransformer(animationType.reverse, animationType.onGetAnim(jSlider))
    }


    /**
     * Set the indicator update mode based on the provided UpdateTypes.
     *
     * @param updateTypes The desired update mode for the indicator.
     */
    fun setIndicatorUpdateTypes(updateTypes: UpdateTypes) {
        indicatorUpdateType = updateTypes.ordinal
    }

    fun setIndicatorShapeTypes(shapeTypes: ShapeTypes) {
        exception()
        this@JSlider.shapeTypes = shapeTypes
    }

    /**
     * Sets the flag indicating whether manual sliding is enabled.
     *
     * @param isEnabled If true, manual sliding is enabled; if false, it follows default behavior.
     */
    fun setManualSlidable(isEnabled: Boolean) {
        manualSlidable = isEnabled
    }

    /**
     * Checks whether manual sliding is currently enabled.
     *
     * @return True if manual sliding is enabled, false otherwise.
     */
    fun isManualSlidableEnabled(): Boolean {
        return manualSlidable
    }


    /**
     * Check if the Slider has been set, and throw an exception if any modification is attempted
     * after the setSlider() method call.
     */
    private fun exception() {
        if (jSlider.adapter != null) throw IllegalArgumentException("You cannot change anything on the Slider after setSlider() method call.")
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
            } else
            // If the window loses focus or auto-sliding is disabled, stop the auto-sliding
                updateHandler.removeCallbacks(update)

        }
        super.onWindowFocusChanged(hasWindowFocus)
    }


    /**
     * Custom LinearLayout that draws a colored circle.
     *
     * @param context The context in which the JIndicator is created.
     */

    private inner class JIndicator(context: Context?, shapeTypes: ShapeTypes) :
        com.jummania.widgets.JIndicator(context, shapeTypes)

    private inner class Slider(context: Context) : com.jummania.widgets.Slider(context)

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
    fun addOnSlideChangeListener(listener: OnSlideChangeListener?) {
        // Set the provided listener as the listener for slide change events in the JSlider
        this@JSlider.listener = listener
    }

    /**
     * Slides the custom slider to the specified position.
     *
     * @param position The position to which the slider should be moved.
     * @param smoothScroll Determines if the transition should be smooth.
     */
    fun slideToPosition(position: Int, smoothScroll: Boolean) {
        // Set the current item of the custom slider to the specified position with optional smooth scroll
        jSlider.setCurrentItem(position, smoothScroll)
    }


    /**
     * This internal companion object is used to hold shared properties related to measuring and sliding.
     */
    internal companion object {
        /**
         * Represents the measure specification to be used for sizing views.
         * Default value is set to 0.
         */
        internal var measureSpec: Int = 0

        /**
         * A flag indicating whether manual sliding is enabled.
         * If true, the sliding behavior is controlled manually; otherwise, it follows default behavior.
         */
        internal var manualSlidable = true
    }


}