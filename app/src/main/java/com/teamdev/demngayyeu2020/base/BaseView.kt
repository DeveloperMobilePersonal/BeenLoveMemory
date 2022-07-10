package com.teamdev.demngayyeu2020.base

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseView<T : ViewDataBinding>(context: Context, attrs: AttributeSet?) :
    FrameLayout(context, attrs) {

    private var _binding: T? = null
    protected val viewBinding get() = _binding!!
    var isDestroy = false

    init {
        install()
    }

    private fun install() {
        val layoutInflater = LayoutInflater.from(context)
        _binding = DataBindingUtil.inflate(layoutInflater, loadUI(), this, false)
        addView(viewBinding.root)
        post {
            createUI()
        }
    }

    protected abstract fun loadUI(): Int
    protected abstract fun createUI()

    override fun onDetachedFromWindow() {
        _binding = null
        isDestroy = true
        super.onDetachedFromWindow()
    }

}