package com.teamdev.demngayyeu2020.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.annotation.StyleRes
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.teamdev.demngayyeu2020.R


abstract class BaseDialog<T : ViewDataBinding>(private val activityCompat: BaseActivity<*>) : AlertDialog(activityCompat) {
    private var _binding: T? = null
    protected val viewBinding get() = _binding!!
    protected var handler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflate = context.let {
            LayoutInflater.from(it)
        }.inflate(layout(), null)
        _binding = DataBindingUtil.bind(inflate)
        _binding?.root?.let {
            setContentView(it)
            val window: Window = window!!
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window.setBackgroundDrawableResource(android.R.color.transparent)
            window.setGravity(gravity())
            if (animate() != -1) {
                window.setWindowAnimations(animate())
            }
            setCanceledOnTouchOutside(cancelOutSide())
            if (disableBack()) {
                setCancelable(false)
            }
            if (fullscreen()) {
                window.setLayout(
                    ConstraintLayout.LayoutParams.MATCH_PARENT,
                    ConstraintLayout.LayoutParams.MATCH_PARENT
                )
            } else {
                window.setLayout(
                    ConstraintLayout.LayoutParams.MATCH_PARENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
                )
            }
            if (showKeyboard()) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
            }
            runUI()
        }

        setOnCancelListener {
            removeUI()
        }
        setOnDismissListener {
            removeUI()
        }
    }

    @LayoutRes
    protected abstract fun layout(): Int

    protected abstract fun runUI()

    @StyleRes
    open fun animate(): Int {
        return R.style.AnimatorScale
    }

    open fun fullscreen(): Boolean {
        return false
    }

    open fun gravity(): Int {
        return Gravity.BOTTOM
    }

    open fun disableBack(): Boolean {
        return false
    }

    open fun showKeyboard(): Boolean {
        return false
    }

    open fun cancelOutSide(): Boolean {
        return true
    }

    open fun showUI() {
        if (!activityCompat.isActive()) {
            return
        }
        if (isShowing) {
            return
        }
        show()
    }

    open fun showDialogCallback(callback: ((Boolean) -> Unit)? = null) {
        showUI()
    }

    open fun hideUI() {
        if (!activityCompat.isActive()) {
            return
        }
        if (!isShowing) {
            return
        }
        dismiss()
    }

    open fun removeUI() {
        handler?.removeCallbacksAndMessages(null)
        handler = null
    }
}