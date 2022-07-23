package com.teamdev.demngayyeu2020.dialog.menulock

import android.view.Gravity
import com.teamdev.demngayyeu2020.R
import com.teamdev.demngayyeu2020.base.BaseActivity
import com.teamdev.demngayyeu2020.base.BaseDialog
import com.teamdev.demngayyeu2020.databinding.DialogTypeLockLayoutBinding
import com.teamdev.demngayyeu2020.ex.*

open class DialogMenuLock(activity: BaseActivity<*>, private val isPost: Boolean = true) :
    BaseDialog<DialogTypeLockLayoutBinding>(activity) {

    var listen: ((Boolean) -> Unit)? = null
    private var typeLock = getKeyTypePass()

    override fun layout(): Int {
        return R.layout.dialog_type_lock_layout
    }

    override fun runUI() {
        viewBinding.rlPin.setOnClickListener {
            typeLock = KEY_PASS_PIN
            if (isPost) {
                Pref.postString(KEY_PASS_TYPE, KEY_PASS_PIN)
            }
            loadCb()
            listen?.let { it(true) }
            dismiss()
        }
        viewBinding.rlDraw.setOnClickListener {
            typeLock = KEY_PASS_DRAW
            if (isPost) {
                Pref.postString(KEY_PASS_TYPE, KEY_PASS_DRAW)
            }
            loadCb()
            listen?.let { it(false) }
            dismiss()
        }
    }

    override fun gravity(): Int {
        return Gravity.CENTER
    }

    override fun showUI() {
        super.showUI()
        loadCb()
    }

    private fun loadCb() {
        if (typeLock == KEY_PASS_DRAW) {
            viewBinding.cbDraw.isChecked = true
            viewBinding.cbPin.isChecked = false
        } else if (typeLock == KEY_PASS_PIN) {
            viewBinding.cbDraw.isChecked = false
            viewBinding.cbPin.isChecked = true
        }
    }
}