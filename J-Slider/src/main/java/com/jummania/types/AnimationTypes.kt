package com.jummania.types

import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.PageTransformer
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
import com.jummania.animations.DepthSlide2
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
 *
 *  * Created by Jummania on 17,November,2023.
 *  * Email: sharifuddinjumman@gmail.com
 *  * Dhaka, Bangladesh.
 *
 * Enumeration representing different animation types for use in sliders.
 * Each constant corresponds to a specific animation style that can be applied to the slider.
 * - [ANTI_CLOCK_SPIN]: Animation with anti-clockwise spinning effect.
 * - [BACKGROUND_TO_FOREGROUND]: Animation transitioning from background to foreground.
 * - [CARD_STACK]: Animation simulating a card stack effect.
 * ... (other animation types)
 * - [ZOOM_OUT]: Zoom out animation effect.
 * - [DEFAULT]: Default animation type.
 */
enum class AnimationTypes(
    private val reverse: Boolean = false,
    private val onGetAnim: (ViewPager) -> PageTransformer?
) {
    ANTI_CLOCK_SPIN(onGetAnim = { AntiClockSpin() }),
    BACKGROUND_TO_FOREGROUND(false, { BackgroundToForeground() }),
    CARD_STACK(false, { CardStack() }),
    CLOCK_SPIN(false, { ClockSpin() }),
    CUBE_IN_DEPTH(false, { CubeInDepth() }),
    CUBE_IN_ROTATION(false, { CubeInRotation() }),
    CUBE_IN_SCALING(false, { CubeInScaling() }),
    CUBE_OUT_DEPTH(false, { CubeOutDepth() }),
    CUBE_OUT_ROTATION(false, { CubeOutRotation() }),
    CUBE_OUT_SCALING(false, { CubeOutScaling() }),
    CUBE_IN(false, { CubeIn() }),
    CUBE_OUT(false, { CubeOut() }),
    DEPTH_SLIDE(false, { DepthSlide() }),
    DEPTH_SLIDE2(false, { pager -> DepthSlide2(pager) }),
    DEPTH_TRANSFORMATION(true, { DepthTransformation() }),
    DEPTH_ZOOM_OUT(false, { DepthZoomOut() }),
    FADEOUT(false, { FadeOut() }),
    FADE_PAGE(false, { FadePage() }),
    FAN_TRANSFORMATION(false, { FanTransformation() }),
    FIDGET_SPINNER(false, { FidgetSpinner() }),
    FLIP_HORIZONTAL(false, { FlipHorizontal() }),
    FLIP_VERTICAL(false, { FlipVertical() }),
    FOREGROUND_TO_BACKGROUND(false, { ForegroundToBackground() }),
    GATE(false, { Gate() }),
    HINGE(true, { Hinge() }),
    POP(false, { Pop() }),
    ROTATE_DOWN(false, { RotateDown() }),
    ROTATE_UP(false, { RotateUp() }),
    SPINNER(false, { Spinner() }),
    SPINNER_TRANSFORMATION(true, { SpinnerTransformation() }),
    TABLET_SLIDE(false, { TabletSlide() }),
    TOSS(false, { Toss() }),
    VERTICAL_FLIP(false, { VerticalFlip() }),
    VERTICAL_SHUT(false, { VerticalShut() }),
    ZOOM_FADE(false, { ZoomFade() }),
    ZOOM_IN(false, { ZoomIn() }),
    ZOOM_OUT(false, { ZoomOut() }),
    DEFAULT(false, { null });

    fun getAnimation(pager: ViewPager,onReceived:(Boolean,PageTransformer) -> Unit) {
        onGetAnim(pager)?.let { onReceived(reverse, it) }
    }
}