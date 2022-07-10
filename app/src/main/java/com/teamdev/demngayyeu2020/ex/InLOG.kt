package com.teamdev.demngayyeu2020.ex

import android.util.Log
import com.teamdev.demngayyeu2020.BuildConfig

fun showLog(clazz: Class<*>, any: Any) {
    if (BuildConfig.DEBUG) {
        Log.i("InLog", "${clazz.simpleName}: $any")
    }
}

fun showLog(clazz: Any, any: Any) {
    if (BuildConfig.DEBUG) {
        Log.i("InLog", "${clazz}: $any")
    }
}