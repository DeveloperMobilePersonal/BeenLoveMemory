package com.teamdev.demngayyeu2020.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.teamdev.demngayyeu2020.`object`.getStatusBarHeight
import com.teamdev.demngayyeu2020.app.ContextApplication
import com.teamdev.demngayyeu2020.ex.height

class StatusView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    init {
        post {
            val statusBarHeight = ContextApplication.get().getStatusBarHeight()
            height(statusBarHeight)
        }
    }
}