package com.teamdev.demngayyeu2020.dialog.date

import android.view.Gravity
import com.teamdev.demngayyeu2020.R
import com.teamdev.demngayyeu2020.base.BaseActivity
import com.teamdev.demngayyeu2020.base.BaseDialog
import com.teamdev.demngayyeu2020.databinding.DialogDateBinding
import com.teamdev.demngayyeu2020.ex.*

class DialogDate(activityCompat: BaseActivity<*>) : BaseDialog<DialogDateBinding>(activityCompat) {

    private var key = ""
    private var date = ""
    private var listener: DateListener? = null

    override fun layout(): Int {
        return R.layout.dialog_date
    }

    override fun runUI() {
        viewBinding.datePicker.setMaxDate(System.currentTimeMillis())
        viewBinding.datePicker.setOnDateSelectedListener { year, month, day ->
            date = "$year-$month-$day"
        }
        viewBinding.btnAllow.click {
            val datePicker = viewBinding.datePicker
            date = "${datePicker.year}-${datePicker.month}-${datePicker.day}"
            listener?.onDateAllow(key, date)
            hideUI()
        }
        viewBinding.btnCancel.click {
            hideUI()
        }
    }

    override fun gravity(): Int {
        return Gravity.CENTER
    }

    fun showUI(key: String, listener: DateListener) {
        this.listener = listener
        this.key = key
        super.showUI()
        when (key) {
            KEY_BIRTHDAY_MALE -> {
                val date = getDateWithString(Pref.birthdayMale) ?: return
                viewBinding.datePicker.setDate(date.first, date.second, date.third, false)
            }
            KEY_BIRTHDAY_FEMALE -> {
                val date = getDateWithString(Pref.birthdayFeMale) ?: return
                viewBinding.datePicker.setDate(date.first, date.second, date.third, false)
            }
            KEY_LOVE_DAY -> {
                val date = getDateWithString(Pref.loveDay) ?: return
                viewBinding.datePicker.setDate(date.first, date.second, date.third, false)
            }
        }
    }

    fun showUI(key: String, listener: DateListener, dateCache: String) {
        this.listener = listener
        this.key = key
        super.showUI()
        val date = getDateWithString(dateCache) ?: return
        viewBinding.datePicker.setDate(date.first, date.second, date.third, false)
    }
}