package com.teamdev.demngayyeu2020.views.clock

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import com.teamdev.demngayyeu2020.R
import com.teamdev.demngayyeu2020.base.BaseView
import com.teamdev.demngayyeu2020.databinding.ClockViewBinding
import com.teamdev.demngayyeu2020.ex.Pref
import com.teamdev.demngayyeu2020.viewanimator.ViewAnimator

class ClockView(context: Context, attrs: AttributeSet?) :
    BaseView<ClockViewBinding>(context, attrs) {

    override fun loadUI(): Int {
        return R.layout.clock_view
    }

    override fun createUI() {
        ViewAnimator.animate(viewBinding.llYear, viewBinding.llWeek)
            .translationY(-10f, 10f).duration(1000)
            .repeatCount(ViewAnimator.INFINITE)
            .repeatMode(ViewAnimator.REVERSE)
            .start()
        ViewAnimator.animate(viewBinding.llMonth, viewBinding.llDay)
            .translationY(10f, -10f).duration(1000)
            .repeatCount(ViewAnimator.INFINITE)
            .repeatMode(ViewAnimator.REVERSE)
            .start()
    }

    @SuppressLint("SetTextI18n")
    fun load(clockModel: ClockModel) {
        viewBinding.tvDateLove.text = "\t${Pref.loveDay}\t"
        viewBinding.tvYear.text = clockModel.year
        viewBinding.tvMonth.text = clockModel.month
        viewBinding.tvWeek.text = clockModel.week
        viewBinding.tvDay.text = clockModel.day
    }
}