package com.teamdev.demngayyeu2020.dialog.menu.letter

import android.view.Gravity
import com.teamdev.demngayyeu2020.R
import com.teamdev.demngayyeu2020.base.BaseActivity
import com.teamdev.demngayyeu2020.base.BaseDialog
import com.teamdev.demngayyeu2020.databinding.DialogMenuLetterBinding
import com.teamdev.demngayyeu2020.ex.click

class DialogMenuLetter(activityCompat: BaseActivity<*>) :
    BaseDialog<DialogMenuLetterBinding>(activityCompat) {

    private var listener: LetterListener? = null
    private var key: String = ""

    override fun layout(): Int {
        return R.layout.dialog_menu_letter
    }

    override fun runUI() {
        viewBinding.ivClose.click {
            hideUI()
        }
        viewBinding.llMenuLetterEdit.click {
            listener?.menuLetterEdit(key)
            hideUI()
        }
        viewBinding.llMenuLetterRotate.click {
            listener?.menuLetterRotate(key)
            hideUI()
        }
    }

    override fun gravity(): Int {
        return Gravity.CENTER
    }

    fun showUI(key: String, listener: LetterListener) {
        this.key = key
        this.listener = listener
        super.showUI()
    }
}