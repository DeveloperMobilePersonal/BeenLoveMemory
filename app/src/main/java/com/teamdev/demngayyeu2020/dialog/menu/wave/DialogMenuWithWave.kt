package com.teamdev.demngayyeu2020.dialog.menu.wave

import android.view.Gravity
import com.teamdev.demngayyeu2020.R
import com.teamdev.demngayyeu2020.base.BaseActivity
import com.teamdev.demngayyeu2020.base.BaseDialog
import com.teamdev.demngayyeu2020.databinding.DialogMenuWithWaveBinding
import com.teamdev.demngayyeu2020.ex.click
import com.teamdev.demngayyeu2020.viewanimator.ViewAnimator

class DialogMenuWithWave(activityCompat: BaseActivity<*>) :
    BaseDialog<DialogMenuWithWaveBinding>(activityCompat) {

    private var listener: MenuWithWaveListener? = null

    fun addListener(listener: MenuWithWaveListener) {
        this.listener = listener
    }

    override fun layout(): Int {
        return R.layout.dialog_menu_with_wave
    }

    override fun runUI() {
        viewBinding.ivClose.click {
            hideUI()
        }
        viewBinding.llStatusTop.click {
            listener?.menuWithWaveEditStatusTop()
            hideUI()
        }
        viewBinding.llStatusBottom.click {
            listener?.menuWithWaveEditStatusBottom()
            hideUI()
        }
        viewBinding.llEditWave.click {
            listener?.menuWithWaveEditWave()
            hideUI()
        }
        viewBinding.llEditWaveColor.click {
            listener?.menuWithWaveEditWaveColor()
            hideUI()
        }
        viewBinding.llEditDateLove.click {
            listener?.menuWithWaveEditDateLove()
            hideUI()
        }
        viewBinding.llEditWallpaper.click {
            listener?.menuWithWaveEditWallpaper()
            hideUI()
        }
        viewBinding.llCapture.click {
            listener?.menuWithWaveCapture()
            hideUI()
        }
    }

    override fun animate(): Int {
        return -1
    }

    override fun gravity(): Int {
        return Gravity.BOTTOM
    }

    override fun showUI() {
        super.showUI()
        ViewAnimator.animate(viewBinding.root).slideBottomInNoneAlpha().duration(250).start()
    }

}