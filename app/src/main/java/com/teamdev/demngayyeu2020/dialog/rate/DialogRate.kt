package com.teamdev.demngayyeu2020.dialog.rate

import android.view.Gravity
import com.teamdev.demngayyeu2020.R
import com.teamdev.demngayyeu2020.base.BaseActivity
import com.teamdev.demngayyeu2020.base.BaseDialog
import com.teamdev.demngayyeu2020.databinding.DialogRateBinding
import com.teamdev.demngayyeu2020.ex.click
import com.teamdev.demngayyeu2020.ex.rate

class DialogRate(val activityCompat: BaseActivity<*>) :
    BaseDialog<DialogRateBinding>(activityCompat) {
    override fun layout(): Int {
        return R.layout.dialog_rate
    }

    override fun runUI() {
        viewBinding.btRate.click {
            activityCompat.rate()
            activityCompat.finish()
        }
    }

    override fun gravity(): Int {
        return Gravity.CENTER
    }

    override fun removeUI() {
        if (activityCompat.isActive()){
            activityCompat.finish()
        }
        super.removeUI()
    }
}