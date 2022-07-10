package com.teamdev.demngayyeu2020.ui.fragment.diary.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teamdev.demngayyeu2020.databinding.ItemDiaryBinding
import com.teamdev.demngayyeu2020.ex.click
import com.teamdev.demngayyeu2020.room.diary.DiaryModel

class DiaryAdapter(private val context: Context) :
    ListAdapter<DiaryModel, DiaryViewHolder>(AsyncDifferConfig.Builder(Diff()).build()) {

    private val layoutInflater by lazy { LayoutInflater.from(context) }
    private var listener: DiaryAdapterListener? = null

    fun addListener(listener: DiaryAdapterListener) {
        this.listener = listener
    }

    class Diff : DiffUtil.ItemCallback<DiaryModel>() {
        override fun areItemsTheSame(oldItem: DiaryModel, newItem: DiaryModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: DiaryModel, newItem: DiaryModel): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryViewHolder {
        return DiaryViewHolder(ItemDiaryBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: DiaryViewHolder, position: Int) {
        holder.bind(currentList[position])
        holder.itemView.click {
            val absoluteAdapterPosition =
                holder.absoluteAdapterPosition.takeIf { it != RecyclerView.NO_POSITION }
                    ?: return@click
            val diaryModel = currentList[absoluteAdapterPosition]
            listener?.onItemDiary(diaryModel)
        }
    }

    override fun submitList(list: MutableList<DiaryModel>?) {
        val l = mutableListOf<DiaryModel>()
        l.addAll(list ?: mutableListOf())
        super.submitList(l)
    }

}