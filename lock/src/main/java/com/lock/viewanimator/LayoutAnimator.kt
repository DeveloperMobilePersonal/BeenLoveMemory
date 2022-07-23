package com.lock.viewanimator

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewPropertyAnimator
import android.view.animation.OvershootInterpolator
import android.widget.FrameLayout

class LayoutAnimator(context: Context, attrs: AttributeSet?) :
    FrameLayout(context, attrs) {

    private var scale = 0.9f
    private var duration = 250L
    private var viewPropertyAnimator: ViewPropertyAnimator? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        try {
            event?.let {
                when (it.action) {
                    0 -> viewPropertyAnimator = animate().scaleX(scale).scaleY(scale)
                    1, 3 -> viewPropertyAnimator = animate().scaleX(1.0f).scaleY(1.0f)
                }
                viewPropertyAnimator?.setDuration(duration)
                    ?.setInterpolator(OvershootInterpolator(1.0f))
            }
        } catch (e: Exception) {

        }
        return super.onTouchEvent(event)
    }
}