package com.teamdev.demngayyeu2020.dialog.input

import android.os.Handler
import android.os.Looper
import android.view.Gravity
import androidx.core.widget.addTextChangedListener
import com.teamdev.demngayyeu2020.R
import com.teamdev.demngayyeu2020.base.BaseActivity
import com.teamdev.demngayyeu2020.base.BaseDialog
import com.teamdev.demngayyeu2020.databinding.DialogInputBinding
import com.teamdev.demngayyeu2020.ex.*
import com.teamdev.demngayyeu2020.ex.Pref.letterMessageCustom
import com.teamdev.demngayyeu2020.ex.Pref.nameFeMale
import com.teamdev.demngayyeu2020.ex.Pref.nameMale
import com.teamdev.demngayyeu2020.ex.Pref.txtBottom
import com.teamdev.demngayyeu2020.ex.Pref.txtTop

class DialogInput(activityCompat: BaseActivity<*>) :
    BaseDialog<DialogInputBinding>(activityCompat) {

    private var key = KEY_TXT_TOP
    private var listener: InputListener? = null

    override fun layout(): Int {
        return R.layout.dialog_input
    }

    override fun runUI() {
        viewBinding.btnAllow.click {
            listener?.onInputAllow(key, viewBinding.edtTitle.text.toString().trim())
            hideUI()
        }
        viewBinding.btnCancel.click {
            hideUI()
        }
        viewBinding.edtTitle.addTextChangedListener {
            val txt = it.toString().trim()
            if (txt.isEmpty() || txt.isBlank()) {
                viewBinding.btnAllow.isEnabled = false
                viewBinding.btnAllow.setBackgroundResource(R.drawable.ripple_btn_allow_no_if)
            } else {
                viewBinding.btnAllow.isEnabled = true
                viewBinding.btnAllow.setBackgroundResource(R.drawable.ripple_btn_allow)
            }
        }
    }

    override fun gravity(): Int {
        return Gravity.CENTER
    }

    override fun showKeyboard(): Boolean {
        return true
    }

    fun showUI(key: String, listener: InputListener) {
        this.listener = listener
        this.key = key
        super.showUI()
        if (handler == null) {
            handler = Handler(Looper.getMainLooper())
        }
        if (key == KEY_TXT_TOP) {
            viewBinding.textInputLayout.hint = getStringCompat(R.string.txt_edit_status_top)
            viewBinding.edtTitle.setText(txtTop)
        } else if (key == KEY_TXT_BOTTOM) {
            viewBinding.textInputLayout.hint = getStringCompat(R.string.txt_edit_status_bottom)
            viewBinding.edtTitle.setText(txtBottom)
        } else if (key == KEY_NAME_MALE) {
            viewBinding.textInputLayout.hint = getStringCompat(R.string.txt_edit_name)
            viewBinding.edtTitle.setText(nameMale)
        } else if (key == KEY_NAME_FEMALE) {
            viewBinding.textInputLayout.hint = getStringCompat(R.string.txt_edit_name)
            viewBinding.edtTitle.setText(nameFeMale)
        } else if (key == KEY_LETTER) {
            viewBinding.textInputLayout.hint = getStringCompat(R.string.txt_edit_letter)
            viewBinding.edtTitle.setText(letterMessageCustom)
        }
        viewBinding.edtTitle.requestFocusWithKeyboard(handler)
    }
}