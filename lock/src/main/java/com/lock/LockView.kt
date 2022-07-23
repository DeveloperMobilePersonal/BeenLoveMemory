package com.lock

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.lock.databinding.PinCodeLayoutBinding
import com.lock.viewanimator.ViewAnimator

class LockView(context: Context, attrs: AttributeSet?) : FrameLayout
    (context, attrs) {

    private lateinit var pinCodeLayoutBinding: PinCodeLayoutBinding
    private var listLayout = mutableListOf<View>()
    private var pass = mutableListOf<String>()
    private var pinCallback: PinCallback? = null
    var delay = false

    init {
        create()
    }

    private fun create() {
        if (!this::pinCodeLayoutBinding.isInitialized) {
            val layoutInflater = LayoutInflater.from(context)
            pinCodeLayoutBinding = PinCodeLayoutBinding.inflate(layoutInflater, this, false)
            addView(pinCodeLayoutBinding.root)
        }
        listLayout.add(pinCodeLayoutBinding.layout0)
        listLayout.add(pinCodeLayoutBinding.layout1)
        listLayout.add(pinCodeLayoutBinding.layout2)
        listLayout.add(pinCodeLayoutBinding.layout3)
        listLayout.add(pinCodeLayoutBinding.layout4)
        listLayout.add(pinCodeLayoutBinding.layout5)
        listLayout.add(pinCodeLayoutBinding.layout6)
        listLayout.add(pinCodeLayoutBinding.layout7)
        listLayout.add(pinCodeLayoutBinding.layout8)
        listLayout.add(pinCodeLayoutBinding.layout9)
        listLayout.add(pinCodeLayoutBinding.layoutX)
        onClick()
        pattern()
        setType()
    }

    private fun onClick() {
        listLayout.forEach {
            it.click {
                val tag = it.tag.toString()
                if (tag == "x" && pass.isNotEmpty()) {
                    pass.remove(pass.last())
                } else {
                    if (pass.size >= 4) {
                        return@click
                    }
                    pass.add(tag)
                }
                if (pass.isEmpty()) {
                    pinCodeLayoutBinding.layoutX.hide()
                    ViewAnimator.animate(pinCodeLayoutBinding.layoutX)
                        .fadeOut()
                        .duration(100)
                        .start()
                } else {
                    val start = ViewAnimator.animate(pinCodeLayoutBinding.layoutX)
                        .bounceIn()
                        .duration(150)
                    pinCodeLayoutBinding.layoutX.show(start)
                }
                setDots()
            }
        }
    }

    private fun pattern() {
        pinCodeLayoutBinding.pattern.setCallBack { password ->
            pinCallback?.onPin(password.list.result(), "key_pass_draw")
            pinCodeLayoutBinding.pattern.reset()
            PatternLockView.CODE_PASSWORD_CORRECT
        }
        pinCodeLayoutBinding.pattern.setOnNodeTouchListener {
            pinCallback?.onDrawChange()
        }
    }

    private fun setDots() {
        when (pass.size) {
            0 -> {
                pinCodeLayoutBinding.ivDot1.srcTransparent()
                pinCodeLayoutBinding.ivDot2.srcTransparent()
                pinCodeLayoutBinding.ivDot3.srcTransparent()
                pinCodeLayoutBinding.ivDot4.srcTransparent()
            }
            1 -> {
                ViewAnimator.animate(pinCodeLayoutBinding.ivDot1)
                    .pulse()
                    .duration(150)
                    .start()
                pinCodeLayoutBinding.ivDot1.srcDot()
                pinCodeLayoutBinding.ivDot2.srcTransparent()
                pinCodeLayoutBinding.ivDot3.srcTransparent()
                pinCodeLayoutBinding.ivDot4.srcTransparent()
            }
            2 -> {
                ViewAnimator.animate(pinCodeLayoutBinding.ivDot2)
                    .pulse()
                    .duration(150)
                    .start()
                pinCodeLayoutBinding.ivDot1.srcDot()
                pinCodeLayoutBinding.ivDot2.srcDot()
                pinCodeLayoutBinding.ivDot3.srcTransparent()
                pinCodeLayoutBinding.ivDot4.srcTransparent()
            }
            3 -> {
                ViewAnimator.animate(pinCodeLayoutBinding.ivDot3)
                    .pulse()
                    .duration(150)
                    .start()
                pinCodeLayoutBinding.ivDot3.srcDot()
                pinCodeLayoutBinding.ivDot2.srcDot()
                pinCodeLayoutBinding.ivDot1.srcDot()
                pinCodeLayoutBinding.ivDot4.srcTransparent()
            }
            4 -> {
                pinCodeLayoutBinding.layoutX.hide()
                pinCodeLayoutBinding.ivDot4.srcDot()
                pinCodeLayoutBinding.ivDot2.srcDot()
                pinCodeLayoutBinding.ivDot3.srcDot()
                pinCodeLayoutBinding.ivDot1.srcDot()
                if (!delay) {
                    pinCallback?.onPin(pass.result(), "key_pass_pin")
                }
                ViewAnimator.animate(pinCodeLayoutBinding.ivDot4)
                    .pulse()
                    .duration(250)
                    .onStop {
                        if (delay) {
                            Log.i("Applock", "setDots: ${pass.result()}")
                            pinCallback?.onPin(pass.result(), "key_pass_pin")
                        }
                        resetPinCode()
                    }
                    .start()
            }
        }
    }

    private fun resetPinCode() {
        pass.clear()
        pinCodeLayoutBinding.layoutX.hide()
        ViewAnimator.animate(pinCodeLayoutBinding.layoutX)
            .fadeOut()
            .duration(100)
            .start()
        setDots()
    }

    fun setType(isPinCode: Boolean = true) {
        if (isPinCode) {
            pinCodeLayoutBinding.layoutPinCode.show()
            pinCodeLayoutBinding.pattern.gone()
        } else {
            pinCodeLayoutBinding.pattern.show()
            pinCodeLayoutBinding.layoutPinCode.gone()
        }
    }

    fun addCallback(callback: PinCallback? = null) {
        pinCallback = callback
    }

    fun sendMessage(txt: String = "") {
        pinCodeLayoutBinding.txtMessage.text = txt
    }

    fun sendMessage2(txt: String = "", animator: Boolean = false) {
        pinCodeLayoutBinding.txtMessage1.text = txt
        if (animator) {
            ViewAnimator.animate(pinCodeLayoutBinding.txtMessage1)
                .translationX(-10f, -5f, 0f, 5f, 10f)
                .duration(250)
                .start()
        }
    }
}