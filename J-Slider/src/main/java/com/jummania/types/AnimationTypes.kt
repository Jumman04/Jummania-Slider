package com.jummania.types

import androidx.viewpager.widget.ViewPager
import com.jummania.animations.AntiClockSpin
import com.jummania.animations.BackgroundToForeground
import com.jummania.animations.CardStack
import com.jummania.animations.ClockSpin
import com.jummania.animations.CubeIn
import com.jummania.animations.CubeInDepth
import com.jummania.animations.CubeInRotation
import com.jummania.animations.CubeInScaling
import com.jummania.animations.CubeOut
import com.jummania.animations.CubeOutDepth
import com.jummania.animations.CubeOutRotation
import com.jummania.animations.CubeOutScaling
import com.jummania.animations.DepthSlide
import com.jummania.animations.DepthTransformation
import com.jummania.animations.DepthZoomOut
import com.jummania.animations.FadeOut
import com.jummania.animations.FadePage
import com.jummania.animations.FanTransformation
import com.jummania.animations.FidgetSpinner
import com.jummania.animations.FlipHorizontal
import com.jummania.animations.FlipVertical
import com.jummania.animations.ForegroundToBackground
import com.jummania.animations.Gate
import com.jummania.animations.Hinge
import com.jummania.animations.Pop
import com.jummania.animations.RotateDown
import com.jummania.animations.RotateUp
import com.jummania.animations.Spinner
import com.jummania.animations.SpinnerTransformation
import com.jummania.animations.TabletSlide
import com.jummania.animations.Toss
import com.jummania.animations.VerticalFlip
import com.jummania.animations.VerticalShut
import com.jummania.animations.ZoomFade
import com.jummania.animations.ZoomIn
import com.jummania.animations.ZoomOut

/**
 * Sealed class representing different animation types for a JSlider.
 *
 *  * Created by Jummania on 17,November,2023.
 *  * Email: sharifuddinjumman@gmail.com
 *  * Dhaka, Bangladesh.
 *
 * @property boolean Boolean indicating whether the reverseDrawingOrder is enabled.
 * @property animation ViewPager.PageTransformer representing the specific animation type.
 */
sealed class AnimationTypes(
    val boolean: Boolean, val animation: ViewPager.PageTransformer? = null
) {
    // Animation types as sealed objects with associated animations
    data object ANTI_CLOCK_SPIN : AnimationTypes(false, AntiClockSpin())
    data object BACKGROUND_TO_FOREGROUND : AnimationTypes(false, BackgroundToForeground())
    data object CARD_STACK : AnimationTypes(false, CardStack())
    data object CLOCK_SPIN : AnimationTypes(false, ClockSpin())
    data object CUBE_IN_DEPTH : AnimationTypes(false, CubeInDepth())
    data object CUBE_IN_ROTATION : AnimationTypes(false, CubeInRotation())
    data object CUBE_IN_SCALING : AnimationTypes(false, CubeInScaling())
    data object CUBE_OUT_DEPTH : AnimationTypes(false, CubeOutDepth())
    data object CUBE_OUT_ROTATION : AnimationTypes(false, CubeOutRotation())
    data object CUBE_OUT_SCALING : AnimationTypes(false, CubeOutScaling())
    data object CUBE_IN : AnimationTypes(false, CubeIn())
    data object CUBE_OUT : AnimationTypes(false, CubeOut())
    data object DEPTH_SLIDE : AnimationTypes(false, DepthSlide())
    data object DEPTH_TRANSFORMATION : AnimationTypes(true, DepthTransformation())
    data object DEPTH_ZOOM_OUT : AnimationTypes(false, DepthZoomOut())
    data object FADE_OUT : AnimationTypes(false, FadeOut())
    data object FADE_PAGE : AnimationTypes(false, FadePage())
    data object FAN_TRANSFORMATION : AnimationTypes(false, FanTransformation())
    data object FIDGET_SPINNER : AnimationTypes(false, FidgetSpinner())
    data object FLIP_HORIZONTAL : AnimationTypes(false, FlipHorizontal())
    data object FLIP_VERTICAL : AnimationTypes(false, FlipVertical())
    data object FOREGROUND_TO_BACKGROUND : AnimationTypes(false, ForegroundToBackground())
    data object GATE : AnimationTypes(false, Gate())
    data object HINGE : AnimationTypes(true, Hinge())
    data object POP : AnimationTypes(false, Pop())
    data object ROTATE_DOWN : AnimationTypes(false, RotateDown())
    data object ROTATE_UP : AnimationTypes(false, RotateUp())
    data object SPINNER : AnimationTypes(false, Spinner())
    data object SPINNER_TRANSFORMATION : AnimationTypes(true, SpinnerTransformation())
    data object TABLET_SLIDE : AnimationTypes(false, TabletSlide())
    data object TOSS : AnimationTypes(false, Toss())
    data object VERTICAL_FLIP : AnimationTypes(false, VerticalFlip())
    data object VERTICAL_SHUT : AnimationTypes(false, VerticalShut())
    data object ZOOM_FADE : AnimationTypes(false, ZoomFade())
    data object ZOOM_IN : AnimationTypes(false, ZoomIn())
    data object ZOOM_OUT : AnimationTypes(false, ZoomOut())

    // Default animation type
    data object DEFAULT : AnimationTypes(false)

    companion object {
        /**
         * Gets the animation type based on the given position.
         *
         * @param position The position of the animation type.
         * @return The corresponding AnimationTypes object.
         */
        fun getAnimation(position: Int): AnimationTypes {

            return when (position) {
                // Mapping positions to corresponding animation types
                0 -> ANTI_CLOCK_SPIN
                1 -> BACKGROUND_TO_FOREGROUND
                2 -> CARD_STACK
                3 -> CLOCK_SPIN
                4 -> CUBE_IN_DEPTH
                5 -> CUBE_IN_ROTATION
                6 -> CUBE_IN_SCALING
                7 -> CUBE_OUT_DEPTH
                8 -> CUBE_OUT_ROTATION
                9 -> CUBE_OUT_SCALING
                10 -> CUBE_IN
                11 -> CUBE_OUT
                12 -> DEPTH_SLIDE
                13 -> DEPTH_TRANSFORMATION
                14 -> DEPTH_ZOOM_OUT
                15 -> FADE_OUT
                16 -> FADE_PAGE
                17 -> FAN_TRANSFORMATION
                18 -> FIDGET_SPINNER
                19 -> FLIP_HORIZONTAL
                20 -> FLIP_VERTICAL
                21 -> FOREGROUND_TO_BACKGROUND
                22 -> GATE
                23 -> HINGE
                24 -> POP
                25 -> ROTATE_DOWN
                26 -> ROTATE_UP
                27 -> SPINNER
                28 -> SPINNER_TRANSFORMATION
                29 -> TABLET_SLIDE
                30 -> TOSS
                31 -> VERTICAL_FLIP
                32 -> VERTICAL_SHUT
                33 -> ZOOM_FADE
                34 -> ZOOM_IN
                35 -> ZOOM_OUT
                else -> DEFAULT
            }

        }

    }
}