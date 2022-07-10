package com.teamdev.demngayyeu2020.`object`

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.WindowManager

fun Activity.statusTransparent() {
    setWindowFlag(this, true)
    window.decorView.systemUiVisibility =
        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    setWindowFlag(this, false)
    window.statusBarColor = Color.TRANSPARENT
}

private fun setWindowFlag(activity: Activity, on: Boolean) {
    val win = activity.window
    val winParams = win.attributes
    if (on) {
        winParams.flags = winParams.flags or WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
    } else {
        winParams.flags =
            winParams.flags and WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS.inv()
    }
    win.attributes = winParams
}

fun Context.getStatusBarHeight(): Int {
    val resId: Int = resources.getIdentifier("status_bar_height", "dimen", "android")
    return if (resId > 0) {
        resources.getDimensionPixelSize(resId)
    } else 80
}

fun Context.getNavigationBarHeight(): Int {
    val resId: Int =
        resources.getIdentifier("navigation_bar_height", "dimen", "android")
    val hasNavBarId: Int = resources.getIdentifier(
        "config_showNavigationBar",
        "bool", "android"
    )
    return if (resId > 0 && hasNavBarId > 0 && resources.getBoolean(hasNavBarId)) {
        resources.getDimensionPixelSize(resId)
    } else 0
}