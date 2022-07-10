package com.teamdev.demngayyeu2020.ex

import android.content.Context
import android.util.DisplayMetrics
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.teamdev.demngayyeu2020.app.ContextApplication

fun Context.getWidth(): Int {
    val metrics: DisplayMetrics = resources.displayMetrics
    return metrics.widthPixels
}

fun showToast(id: Int) {
    Toast.makeText(ContextApplication.get(), getStringCompat(id), Toast.LENGTH_SHORT).show()
}

fun getColorCompat(color: Int): Int {
    return ResourcesCompat.getColor(ContextApplication.get().resources, color, null)
}

fun getStringCompat(id: Int, param: String? = null): String {
    return if (param == null) {
        ContextApplication.get().getString(id)
    } else {
        ContextApplication.get().getString(id, param)
    }
}