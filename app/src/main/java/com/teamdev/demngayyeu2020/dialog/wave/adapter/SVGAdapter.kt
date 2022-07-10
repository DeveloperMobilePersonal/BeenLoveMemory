package com.teamdev.demngayyeu2020.dialog.wave.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.teamdev.demngayyeu2020.databinding.ItemSvgBinding
import com.teamdev.demngayyeu2020.dialog.wave.SVGListener
import com.teamdev.demngayyeu2020.dialog.wave.SVGModel
import com.teamdev.demngayyeu2020.ex.click

class SVGAdapter(val context: Context) : BaseAdapter() {

    val currentList = mutableListOf<SVGModel>()
    private var layoutInflater: LayoutInflater? = null
    private var listener: SVGListener? = null

    fun reloadList(currentList: MutableList<SVGModel>, listener: SVGListener): Boolean {
        this.listener = listener
        if (currentList == this.currentList) return false
        this.currentList.clear()
        this.currentList.addAll(currentList)
        notifyDataSetChanged()
        return true
    }

    private fun createLayoutInflater() {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(context)
        }
    }

    override fun getCount(): Int {
        return currentList.size
    }

    override fun getItem(p0: Int): Any {
        return currentList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        createLayoutInflater()
        val viewBinding = ItemSvgBinding.inflate(layoutInflater!!, parent, false)
        val svgModel = currentList[position]
        viewBinding.model = svgModel
        viewBinding.root.click {
            listener?.itemSVGAllow("", svgModel.id)
        }
        viewBinding.executePendingBindings()
        return viewBinding.root
    }
}