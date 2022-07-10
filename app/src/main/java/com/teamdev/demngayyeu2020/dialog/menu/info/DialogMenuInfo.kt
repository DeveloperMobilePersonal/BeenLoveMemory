package com.teamdev.demngayyeu2020.dialog.menu.info

import android.view.Gravity
import com.teamdev.demngayyeu2020.R
import com.teamdev.demngayyeu2020.base.BaseActivity
import com.teamdev.demngayyeu2020.base.BaseDialog
import com.teamdev.demngayyeu2020.databinding.DialogMenuInfoBinding
import com.teamdev.demngayyeu2020.ex.click

class DialogMenuInfo(activityCompat: BaseActivity<*>) :
    BaseDialog<DialogMenuInfoBinding>(activityCompat) {

    private var listener: MenuInfoListener? = null
    private var key: String = ""

    override fun layout(): Int {
        return R.layout.dialog_menu_info
    }

    override fun runUI() {
        viewBinding.ivClose.click {
            hideUI()
        }
        viewBinding.llMenuInfoEditFace.click {
            listener?.menuInfoEditFace(key)
            hideUI()
        }
        viewBinding.llMenuInfoEditFrame.click {
            listener?.menuInfoEditFaceFrame(key)
            hideUI()
        }
        viewBinding.llMenuInfoEditName.click {
            listener?.menuInfoEditName(key)
            hideUI()
        }
    }

    override fun gravity(): Int {
        return Gravity.CENTER
    }

    fun showUI(key: String, listener: MenuInfoListener) {
        this.key = key
        this.listener = listener
        super.showUI()
    }
}