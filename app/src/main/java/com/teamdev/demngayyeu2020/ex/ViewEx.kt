package com.teamdev.demngayyeu2020.ex

import android.os.Handler
import android.os.SystemClock
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.teamdev.demngayyeu2020.R

fun View.click(result: ((View) -> Unit)) {
    setOnClickListener {
        isEnabled = false
        result(this)
        kotlin.runCatching { postDelayed({ isEnabled = true }, 300) }
    }
}

fun View.disableDoubleClick() {
    isEnabled = false
    kotlin.runCatching {
        postDelayed({
            isEnabled = true
        }, 300)
    }
}

fun View.height(size: Int) {
    var layoutParams = this.layoutParams
    if (layoutParams == null) {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }
    layoutParams.height = size
    this.layoutParams = layoutParams
}

fun View.width(size: Int) {
    var layoutParams = this.layoutParams
    if (layoutParams == null) {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }
    layoutParams.width = size
    this.layoutParams = layoutParams
}

fun View.hide() {
    if (visibility == View.INVISIBLE) return
    visibility = View.INVISIBLE
}

fun View.show() {
    if (visibility == View.VISIBLE) return
    visibility = View.VISIBLE
}

fun View.gone() {
    if (visibility == View.GONE) return
    visibility = View.GONE
}

fun View.isShow(): Boolean {
    return visibility == View.VISIBLE
}

fun View.mainSelect(){
    setBackgroundResource(R.drawable.shape_select)
}

fun View.mainUnSelect(){
    setBackgroundResource(R.drawable.shape_un_select)
}

fun EditText.requestFocusWithKeyboard(handler: Handler?) {
    handler?.postDelayed({
        kotlin.runCatching {
            dispatchTouchEvent(
                MotionEvent.obtain(
                    SystemClock.uptimeMillis(),
                    SystemClock.uptimeMillis(),
                    MotionEvent.ACTION_DOWN,
                    0f,
                    0f,
                    0
                )
            )
            dispatchTouchEvent(
                MotionEvent.obtain(
                    SystemClock.uptimeMillis(),
                    SystemClock.uptimeMillis(),
                    MotionEvent.ACTION_UP,
                    0f,
                    0f,
                    0
                )
            )
            setSelection(length())
        }
    }, 350)
}
