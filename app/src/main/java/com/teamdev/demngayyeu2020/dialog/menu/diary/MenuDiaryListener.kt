package com.teamdev.demngayyeu2020.dialog.menu.diary

import com.teamdev.demngayyeu2020.room.diary.DiaryModel

interface MenuDiaryListener {
    fun menuDiaryEdit(diaryModel: DiaryModel)
    fun menuDiaryDelete(diaryModel: DiaryModel)
}