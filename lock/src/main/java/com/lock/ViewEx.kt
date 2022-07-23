package com.lock

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import com.lock.viewanimator.AnimationBuilder

fun View.click(click: (() -> Unit)? = null) {
    setOnClickListener {
        isEnabled = false
        click?.let { it() }
        postDelayed({
            isEnabled = true
        }, 10)
    }
}

fun View.show(viewAnimator: AnimationBuilder? = null) {
    if (visibility == View.VISIBLE) return
    visibility = View.VISIBLE
    viewAnimator?.start()
}

fun View.hide() {
    if (visibility == View.INVISIBLE) return
    visibility = View.INVISIBLE
}

fun View.gone() {
    if (visibility == View.GONE) return
    visibility = View.GONE
}

fun AppCompatImageView.srcTransparent() {
    setImageDrawable(ColorDrawable(Color.TRANSPARENT))
}

fun AppCompatImageView.srcDot() {
    setImageResource(R.drawable.shape_circle)
}