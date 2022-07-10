package com.teamdev.demngayyeu2020.ui.crop.adapter

import androidx.recyclerview.widget.RecyclerView
import com.teamdev.demngayyeu2020.databinding.ItemGalleryBinding
import com.teamdev.demngayyeu2020.ex.loadCacheAll
import com.teamdev.demngayyeu2020.scan.PhotoModel

class GalleryHolder(private val viewBinding: ItemGalleryBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {

    fun bind(photoModel: PhotoModel) {
        viewBinding.ivPreview.loadCacheAll(photoModel.path)
    }
}