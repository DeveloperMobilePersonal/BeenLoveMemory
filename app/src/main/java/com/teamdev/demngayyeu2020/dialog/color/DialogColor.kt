package com.teamdev.demngayyeu2020.dialog.color

import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import com.teamdev.demngayyeu2020.R
import com.teamdev.demngayyeu2020.base.BaseActivity
import com.teamdev.demngayyeu2020.base.BaseDialog
import com.teamdev.demngayyeu2020.databinding.DialogColorBinding
import com.teamdev.demngayyeu2020.ex.*

class DialogColor(activityCompat: BaseActivity<*>) :
    BaseDialog<DialogColorBinding>(activityCompat) {

    private var key = ""
    private var listener: ColorListener? = null

    override fun layout(): Int {
        return R.layout.dialog_color
    }

    override fun runUI() {
        var color = Color.parseColor(COLOR_DEFAULT)
        viewBinding.colorPickerView.setShowBorder(true)
        viewBinding.colorPickerView.setDensity(12)
        viewBinding.colorPickerView.addOnColorChangedListener {
            color = it
        }
        viewBinding.btnAllow.click {
            listener?.onAllowColor(key, color)
            hideUI()
        }
        viewBinding.btnCancel.click {
            hideUI()
        }
    }

    override fun gravity(): Int {
        return Gravity.CENTER
    }

    fun showUI(key: String, listener: ColorListener) {
        this.listener = listener
        this.key = key
        super.showUI()
        if (handler == null) {
            handler = Handler(Looper.getMainLooper())
        }
        handler?.post {
            when (key) {
                KEY_AGE_MALE -> {
                    val ageMaleBgColor = Pref.ageMaleBgColor
                    viewBinding.vLightnessSlider.setColor(ageMaleBgColor)
                    viewBinding.colorPickerView.setInitialColor(ageMaleBgColor, true)
                }
                KEY_AGE_FEMALE -> {
                    val ageFeMaleBgColor = Pref.ageFeMaleBgColor
                    viewBinding.vLightnessSlider.setColor(ageFeMaleBgColor)
                    viewBinding.colorPickerView.setInitialColor(ageFeMaleBgColor, true)
                }
                KEY_BOW_MALE -> {
                    val ageMaleBgColor = Pref.bowMaleBgColor
                    viewBinding.vLightnessSlider.setColor(ageMaleBgColor)
                    viewBinding.colorPickerView.setInitialColor(ageMaleBgColor, true)
                }
                KEY_BOW_FEMALE -> {
                    val ageFeMaleBgColor = Pref.bowFeMaleBgColor
                    viewBinding.vLightnessSlider.setColor(ageFeMaleBgColor)
                    viewBinding.colorPickerView.setInitialColor(ageFeMaleBgColor, true)
                }
                KEY_WAVE_COLOR -> {
                    val waveColor = Pref.waveColor
                    viewBinding.vLightnessSlider.setColor(waveColor)
                    viewBinding.colorPickerView.setInitialColor(waveColor, true)
                }
            }
        }
    }
}