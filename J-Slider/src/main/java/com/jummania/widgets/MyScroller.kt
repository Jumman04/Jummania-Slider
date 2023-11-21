package com.jummania.widgets

import android.content.Context
import android.widget.Scroller

/**
 * Custom Scroller class that overrides the startScroll method to use a custom sliding duration.
 *  * Created by Jummania on 20,November,2023.
 *  * Email: sharifuddinjumman@gmail.com
 *  * Dhaka, Bangladesh.
 *
 * @param context The context in which the Scroller is created.
 */
internal class MyScroller(context: Context?, private val slidingDuration: Int) : Scroller(context) {

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
        super.startScroll(startX, startY, dx, dy, slidingDuration)
    }
}