package com.teamdev.demngayyeu2020.ui.bow.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.teamdev.demngayyeu2020.databinding.ItemBowBinding
import com.teamdev.demngayyeu2020.ex.click
import com.teamdev.demngayyeu2020.ex.getBowList
import com.teamdev.demngayyeu2020.ex.loadCacheAll

class BowAdapter(private val context: Context) : BaseAdapter() {

    private val list = getBowList()
    private val layoutInflater: LayoutInflater by lazy {
        LayoutInflater.from(context)
    }
    private var listener:BowListener? = null

    fun addListener(listener:BowListener){
        this.listener = listener
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, v: View?, parent: ViewGroup?): View {
        val viewBinding = ItemBowBinding.inflate(layoutInflater, parent, false)
        val bowModel = list[position]
        viewBinding.tvTitle.text = bowModel.name
        viewBinding.ivLogo.loadCacheAll(bowModel.icon)
        viewBinding.root.click {
            listener?.onItemClick(bowModel)
        }
        return viewBinding.root
    }
}