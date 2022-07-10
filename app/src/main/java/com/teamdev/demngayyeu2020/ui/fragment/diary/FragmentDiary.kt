package com.teamdev.demngayyeu2020.ui.fragment.diary

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teamdev.demngayyeu2020.R
import com.teamdev.demngayyeu2020.base.BaseFragment
import com.teamdev.demngayyeu2020.databinding.FragmentDiaryBinding
import com.teamdev.demngayyeu2020.dialog.menu.diary.MenuDiaryListener
import com.teamdev.demngayyeu2020.ex.click
import com.teamdev.demngayyeu2020.ex.showToast
import com.teamdev.demngayyeu2020.room.diary.DiaryModel
import com.teamdev.demngayyeu2020.ui.diary.DiaryActivity
import com.teamdev.demngayyeu2020.ui.fragment.diary.adapter.DiaryAdapterListener
import java.io.File

class FragmentDiary : BaseFragment<FragmentDiaryBinding>(),
    DiaryAdapterListener,
    MenuDiaryListener {

    private var isLoading = false

    override fun loadUI(): Int {
        return R.layout.fragment_diary
    }

    override fun createUI() {
        runMainActivity { activity ->
            activity.diaryAdapter.addListener(this)
            viewBinding.recyclerView.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            viewBinding.recyclerView.adapter = activity.diaryAdapter
            viewBinding.floatingActionButton.click {
                val intent = Intent(activity, DiaryActivity::class.java)
                activity.startActivity(intent)
            }
            viewBinding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dy > 0) viewBinding.floatingActionButton.hide()
                    else viewBinding.floatingActionButton.show()
                    super.onScrolled(recyclerView, dx, dy)
                }
            })
        }
    }

    override fun onResume() {
        super.onResume()
        if (!isLoading) {
            runMainActivity { activity ->
                activity.roomManager.getLiveListDiary().observe(this) {
                    if (isActive()) {
                        activity.diaryAdapter.submitList(it)
                    }
                }
            }
        }
    }

    override fun destroyUI() {
        runMainActivity { activity ->
            activity.roomManager.getLiveListDiary().removeObservers(this)
        }
    }

    override fun onItemDiary(diaryModel: DiaryModel) {
        runMainActivity { activity ->
            activity.diaryMenuDiary.showUI(diaryModel, this)
        }
    }

    override fun menuDiaryEdit(diaryModel: DiaryModel) {
        runMainActivity { activity ->
            val intent = Intent(activity, DiaryActivity::class.java)
            intent.putExtra(DiaryActivity.KEY_DIARY_ID, diaryModel.id.toString())
            startActivity(intent)
        }
    }

    override fun menuDiaryDelete(diaryModel: DiaryModel) {
        runMainActivity { activity ->
            val file = File(diaryModel.path)
            if (file.exists()) file.delete()
            activity.roomManager.deleteDiary(diaryModel)
            showToast(R.string.txt_delete_success)
        }
    }
}