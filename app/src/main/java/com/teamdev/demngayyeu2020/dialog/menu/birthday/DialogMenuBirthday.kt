package com.teamdev.demngayyeu2020.dialog.menu.birthday

import android.view.Gravity
import com.teamdev.demngayyeu2020.R
import com.teamdev.demngayyeu2020.base.BaseActivity
import com.teamdev.demngayyeu2020.base.BaseDialog
import com.teamdev.demngayyeu2020.databinding.DialogMenuBirthdayBinding
import com.teamdev.demngayyeu2020.ex.click

class DialogMenuBirthday(activityCompat: BaseActivity<*>) :
    BaseDialog<DialogMenuBirthdayBinding>(activityCompat) {

    private var listener: MenuBirthdayListener? = null
    private var key: String = ""

    override fun layout(): Int {
        return R.layout.dialog_menu_birthday
    }

    override fun gravity(): Int {
        return Gravity.CENTER
    }

    override fun runUI() {
        viewBinding.ivClose.click {
            hideUI()
        }
        viewBinding.llMenuBirthdayEdit.click {
            listener?.menuBirthdayEdit(key)
            hideUI()
        }
        viewBinding.llMenuBirthdayEditBg.click {
            listener?.menuBirthdayEditBg(key)
            hideUI()
        }
        viewBinding.llMenuBirthdayEditSex.click {
            listener?.menuBirthdayEditSex()
            hideUI()
        }
    }

    fun showUI(key: String, listener: MenuBirthdayListener) {
        this.listener = listener
        this.key = key
        super.showUI()
    }
}