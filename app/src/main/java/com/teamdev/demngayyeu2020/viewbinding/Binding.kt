package com.teamdev.demngayyeu2020.viewbinding

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import com.github.siyamed.shapeimageview.ShapeImageView
import com.teamdev.demngayyeu2020.R
import com.teamdev.demngayyeu2020.ex.Pref
import com.teamdev.demngayyeu2020.ex.getDateLoveDetails
import com.teamdev.demngayyeu2020.ex.getStringCompat
import com.teamdev.demngayyeu2020.ex.loadCacheAll
import com.teamdev.demngayyeu2020.views.WaveLoadingView
import com.teamdev.demngayyeu2020.views.clock.ClockView

object Binding {
    @JvmStatic
    @BindingAdapter("loadImage")
    fun loadImage(imageView: ImageView, id: Any?) {
        if (id == null) {
            return
        }
        imageView.loadCacheAll(id)
    }

    @JvmStatic
    @BindingAdapter("setShape", "setImage")
    fun ShapeImageView.loadShapeImageView(id: Int?, path: Any?) {
        if (path == null || id == null) {
            return
        }
        setShapeResId(id)
        loadCacheAll(path)
    }

    @JvmStatic
    @BindingAdapter("loadBackground")
    fun loadBackground(view: View, id: Drawable?) {
        if (id == null) {
            return
        }
        view.background = id
    }

    @JvmStatic
    @BindingAdapter("loadSexLocation1")
    fun loadSexLocation1(view: ImageView, state: Boolean?) {
        if (state == null) {
            return
        }
        val icon = if (state) {
            R.drawable.ic_sex_male
        } else {
            R.drawable.ic_sex_female
        }
        view.loadCacheAll(icon)
    }

    @JvmStatic
    @BindingAdapter("loadSexLocation2")
    fun loadSexLocation2(view: ImageView, state: Boolean?) {
        if (state == null) {
            return
        }
        val icon = if (state) {
            R.drawable.ic_sex_female
        } else {
            R.drawable.ic_sex_male
        }
        view.loadCacheAll(icon)
    }

    @JvmStatic
    @BindingAdapter("wlvProgressValue")
    fun wlvProgressValue(waveLoadingView: WaveLoadingView, value: String?) {
        if (value == null) {
            return
        }
        kotlin.runCatching {
            val progress = value.toInt()
            when {
                progress <= 100 -> {
                    waveLoadingView.progressValue = progress
                }
                progress <= 1000 -> {
                    waveLoadingView.progressValue = progress / 10
                }
                progress <= 10000 -> {
                    waveLoadingView.progressValue = progress / 100
                }
                progress <= 100000 -> {
                    waveLoadingView.progressValue = progress / 1000
                }
            }
        }
    }

    @JvmStatic
    @BindingAdapter("loadClock")
    fun loadClock(clockView: ClockView, loveDay: String?) {
        if (loveDay == null) {
            return
        }
        val dateLoveDetails = getDateLoveDetails(Pref.loveDay)
        clockView.load(dateLoveDetails)
    }

    @SuppressLint("SetTextI18n")
    @JvmStatic
    @BindingAdapter("setName1", "setName2", "letterNameLocation")
    fun AppCompatTextView.loadLetter(name1: String?, name2: String?, location: Boolean?) {
        if (name1 == null || name2 == null || location == null) {
            return
        }
        text = if (Pref.letterNameLocation) {
            "${getStringCompat(R.string.txt_send)} ${Pref.nameMale.trim()},"
        } else {
            "${getStringCompat(R.string.txt_send)} ${Pref.nameFeMale.trim()},"
        }
    }

    @SuppressLint("SetTextI18n")
    @JvmStatic
    @BindingAdapter("setName3", "setName4", "letterNameLocation2")
    fun AppCompatTextView.loadLetter2(name1: String?, name2: String?, location: Boolean?) {
        if (name1 == null || name2 == null || location == null) {
            return
        }
        text = if (Pref.letterNameLocation) {
            "${getStringCompat(R.string.txt_love_forever)}, ${Pref.nameFeMale.trim()}."
        } else {
            "${getStringCompat(R.string.txt_love_forever)}, ${Pref.nameMale.trim()}."
        }
    }
}