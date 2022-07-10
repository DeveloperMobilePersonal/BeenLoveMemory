package com.teamdev.demngayyeu2020.ui.fragment.diary.adapter

import androidx.recyclerview.widget.RecyclerView
import com.teamdev.demngayyeu2020.databinding.ItemDiaryBinding
import com.teamdev.demngayyeu2020.room.diary.DiaryModel

class DiaryViewHolder(val viewBinding: ItemDiaryBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {
    fun bind(diaryModel: DiaryModel) {
        viewBinding.timeline.initLine(0)
        viewBinding.model = diaryModel
    }
}