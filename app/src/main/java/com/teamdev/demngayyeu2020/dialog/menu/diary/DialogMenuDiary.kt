package com.teamdev.demngayyeu2020.dialog.menu.diary

import android.view.Gravity
import com.teamdev.demngayyeu2020.R
import com.teamdev.demngayyeu2020.base.BaseActivity
import com.teamdev.demngayyeu2020.base.BaseDialog
import com.teamdev.demngayyeu2020.databinding.DialogMenuDiaryBinding
import com.teamdev.demngayyeu2020.ex.click
import com.teamdev.demngayyeu2020.room.diary.DiaryModel

class DialogMenuDiary(activityCompat: BaseActivity<*>) :
    BaseDialog<DialogMenuDiaryBinding>(activityCompat) {

    override fun layout(): Int {
        return R.layout.dialog_menu_diary
    }

    override fun runUI() {
        viewBinding.ivClose.click {
            hideUI()
        }
    }

    override fun gravity(): Int {
        return Gravity.CENTER
    }

    fun showUI(diaryModel: DiaryModel, listener: MenuDiaryListener?) {
        super.showUI()
        viewBinding.llMenuDiaryEdit.click {
            listener?.menuDiaryEdit(diaryModel)
            hideUI()
        }
        viewBinding.llMenuDiaryDelete.click {
            listener?.menuDiaryDelete(diaryModel)
            hideUI()
        }
    }
}