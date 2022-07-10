package com.teamdev.demngayyeu2020.ex

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable

fun createShape(radius: Float, color: Int): Drawable {
    return runCatching {
        val intArray = IntArray(2)
        intArray[0] = color
        intArray[1] = color
        val gradientDrawable = GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, intArray)
        gradientDrawable.cornerRadius = radius
        gradientDrawable
    }.getOrElse { ColorDrawable(color) }
}