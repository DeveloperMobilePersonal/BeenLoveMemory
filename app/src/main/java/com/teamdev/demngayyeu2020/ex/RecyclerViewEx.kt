package com.teamdev.demngayyeu2020.ex

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.teamdev.demngayyeu2020.ui.crop.adapter.GalleryAdapter

fun RecyclerView.spacing(idRes:Int){
    val spacing = resources.getDimensionPixelSize(idRes) / 2
    setPadding(spacing, spacing, spacing, spacing)
    clipToPadding = false
    clipChildren = false
    addItemDecoration(object : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            outRect.set(spacing, spacing, spacing, spacing)
        }
    })
}

fun RecyclerView.disableAnimator() {
    val itemAnimator = itemAnimator
    if (itemAnimator is SimpleItemAnimator) {
        itemAnimator.supportsChangeAnimations = false
    }
}

fun RecyclerView.enableAnimator() {
    val itemAnimator = itemAnimator
    if (itemAnimator is SimpleItemAnimator) {
        itemAnimator.supportsChangeAnimations = true
    }
}

fun RecyclerView.gridLayoutManager(albumAdapter: GalleryAdapter, spanCount:Int ){
    val flexboxLayoutManager = GridLayoutManager(context, spanCount)
    layoutManager = flexboxLayoutManager
    adapter = albumAdapter
}