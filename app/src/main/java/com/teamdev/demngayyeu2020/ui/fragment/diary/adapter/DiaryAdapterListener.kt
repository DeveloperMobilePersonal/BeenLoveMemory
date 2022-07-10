package com.teamdev.demngayyeu2020.ui.fragment.diary.adapter

import com.teamdev.demngayyeu2020.room.diary.DiaryModel

interface DiaryAdapterListener {
    fun onItemDiary(diaryModel: DiaryModel)
}