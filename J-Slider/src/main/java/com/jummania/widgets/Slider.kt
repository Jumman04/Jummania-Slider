package com.jummania.widgets

import android.content.Context
import androidx.viewpager.widget.ViewPager
import com.jummania.JSlider.Companion.measureSpec

/**
 * Abstract class representing a custom ViewPager with adjusted height.
 *  * Created by Jummania on 20,November,2023.
 *  * Email: sharifuddinjumman@gmail.com
 *  * Dhaka, Bangladesh.
 *
 * @param context The context in which the Slider is created.
 */
internal abstract class Slider(context: Context) : ViewPager(context) {

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
        } else
        // If the height mode is EXACTLY or unspecified, use the original onMeasure
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)

    }
}
