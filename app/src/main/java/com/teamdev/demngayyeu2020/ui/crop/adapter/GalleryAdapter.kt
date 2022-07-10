package com.teamdev.demngayyeu2020.ui.crop.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamdev.demngayyeu2020.databinding.ItemGalleryBinding
import com.teamdev.demngayyeu2020.ex.click
import com.teamdev.demngayyeu2020.ex.showLog
import com.teamdev.demngayyeu2020.scan.PhotoModel

class GalleryAdapter(private val context: Context) : RecyclerView.Adapter<GalleryHolder>() {

    private val layoutInflater by lazy { LayoutInflater.from(context) }
    private val list = mutableListOf<PhotoModel>()
    private var listener:GalleryListener? = null

    fun addListener(listener: GalleryListener){
        this.listener = listener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addList(list: MutableList<PhotoModel>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryHolder {
        return GalleryHolder(ItemGalleryBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: GalleryHolder, position: Int) {
        val photoModel = list[position]
        holder.bind(photoModel)
        holder.itemView.click {
            listener?.onGalleryItem(photoModel)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}