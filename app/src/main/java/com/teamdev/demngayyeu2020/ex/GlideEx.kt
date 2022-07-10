package com.teamdev.demngayyeu2020.ex

import android.graphics.Bitmap
import android.graphics.Color
import android.widget.ImageView
import androidx.annotation.UiThread
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.teamdev.demngayyeu2020.R
import com.teamdev.demngayyeu2020.app.ContextApplication

fun ImageView.loadCacheAll(path: Any, placeholder: Int = R.drawable.shape_loading_glide) {
    val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
    Glide.with(ContextApplication.get())
        .load(path)
        .placeholder(placeholder)
        .apply(requestOptions)
        .into(this)
}

fun ImageView.loadCacheNone(path: Any, placeholder: Int = R.drawable.shape_loading_glide) {
    val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE)
    Glide.with(ContextApplication.get())
        .load(path)
        .placeholder(placeholder)
        .skipMemoryCache(true)
        .apply(requestOptions)
        .into(this)
}

fun ImageView.loadGif(path: Any, placeholder: Int = R.drawable.shape_loading_glide) {
    val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
    Glide.with(ContextApplication.get())
        .asGif()
        .load(path)
        .placeholder(placeholder)
        .apply(requestOptions)
        .into(this)
}

fun ImageView.clear() {
    try {
        Glide.with(ContextApplication.get()).clear(this)
        setImageDrawable(null)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun loadBitmapCircleCrop(
    path: Any, resize: Int
): Bitmap {
    val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
    return Glide.with(ContextApplication.get())
        .asBitmap()
        .load(path)
        .circleCrop()
        .apply(requestOptions)
        .submit(resize, resize).get()
}