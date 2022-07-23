package com.teamdev.demngayyeu2020.ui.bow

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.teamdev.demngayyeu2020.R
import com.teamdev.demngayyeu2020.base.BaseActivity
import com.teamdev.demngayyeu2020.databinding.ActivityBowBinding
import com.teamdev.demngayyeu2020.ex.click
import com.teamdev.demngayyeu2020.ui.bow.adapter.BowAdapter
import com.teamdev.demngayyeu2020.ui.bow.adapter.BowListener
import com.teamdev.demngayyeu2020.ui.bow.adapter.BowModel
import com.teamdev.demngayyeu2020.ui.lock.SetUpActivity
import org.koin.android.ext.android.inject

class BowActivity : BaseActivity<ActivityBowBinding>(), BowListener {

    companion object {
        fun open(context: Context) {
            val intent = Intent(context, BowActivity::class.java)
            context.startActivity(intent)
        }
    }

    private val bowAdapter by inject<BowAdapter>()

    override fun loadUI(): Int {
        return R.layout.activity_bow
    }

    override fun createUI() {
        bowAdapter.addListener(this)
        viewBinding.gridView.adapter = bowAdapter
        viewBinding.ivBack.click {
            finish()
        }
    }

    override fun destroyUI() {

    }

    override fun onItemClick(bowModel: BowModel) {

    }
}