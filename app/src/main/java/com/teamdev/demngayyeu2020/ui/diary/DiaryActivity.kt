package com.teamdev.demngayyeu2020.ui.diary

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.teamdev.demngayyeu2020.R
import com.teamdev.demngayyeu2020.base.BaseActivity
import com.teamdev.demngayyeu2020.databinding.ActivityDiaryBinding
import com.teamdev.demngayyeu2020.dialog.date.DateListener
import com.teamdev.demngayyeu2020.dialog.date.DialogDate
import com.teamdev.demngayyeu2020.ex.*
import com.teamdev.demngayyeu2020.room.RoomManager
import com.teamdev.demngayyeu2020.room.diary.DiaryModel
import com.teamdev.demngayyeu2020.ui.crop.CropActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import java.io.File

class DiaryActivity : BaseActivity<ActivityDiaryBinding>(), DateListener {

    companion object {
        const val KEY_DIARY_ID = "diary_type_id"
    }

    private val takePhotoForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK && isActive()) {
                val intent = result.data ?: return@registerForActivityResult
                val key = intent.getStringExtra(CropActivity.CROP)
                val path = intent.getStringExtra(CropActivity.PATH) ?: ""
                val file = File(this.path)
                if (file.exists()) file.delete()
                this.path = path
                when (key) {
                    KEY_DIARY -> {
                        viewBinding.ivPreview.loadCacheAll(path)
                        viewBinding.ivPreviewCamera.gone()
                    }
                }
            }
        }

    private val dialogDate by inject<DialogDate>()
    private val roomManager by inject<RoomManager>()
    private var path = ""
    private var diaryModelCache: DiaryModel? = null

    override fun loadUI(): Int {
        return R.layout.activity_diary
    }

    override fun createUI() {
        val id = intent.getStringExtra(KEY_DIARY_ID) ?: ""
        if (id.isNotEmpty()) {
            lifecycleScope.launch(Dispatchers.IO) {
                diaryModelCache = roomManager.getDiary(id.toLong())
                withContext(Dispatchers.Main) {
                    if (isActive()) {
                        diaryModelCache?.apply {
                            viewBinding.ivPreview.loadCacheAll(path)
                            viewBinding.ivPreviewCamera.gone()
                            viewBinding.tvContent.setText(content)
                            viewBinding.tvSelectDate.text = date
                        }
                    }
                }
            }
        }
        viewBinding.ivBack.click {
            onBackPressed()
        }
        viewBinding.tvSelectDate.click {
            if (diaryModelCache == null) {
                dialogDate.showUI(KEY_DIARY, this)
            } else {
                dialogDate.showUI(KEY_DIARY, this, diaryModelCache!!.date)
            }
        }
        viewBinding.crSelectImage.click {
            val intent = Intent(this, CropActivity::class.java)
            intent.putExtra(CropActivity.CROP, KEY_DIARY)
            takePhotoForResult.launch(intent)
        }
        viewBinding.btnSave.click {
            val message = viewBinding.tvContent.text.toString().trim()
            val date = viewBinding.tvSelectDate.text.toString().trim()
            if (path.isEmpty() && diaryModelCache != null) {
                path = diaryModelCache!!.path
            }
            if (message.isEmpty() || date.isEmpty() || path.isEmpty()) {
                showToast(R.string.txt_content)
                return@click
            }
            if (diaryModelCache == null) {
                val diaryModel = DiaryModel(0, date, message, path)
                roomManager.insertDiary(diaryModel)
                showToast(R.string.txt_create_success)
                finish()
            } else {
                val diaryModel = DiaryModel(
                    diaryModelCache!!.id,
                    date,
                    message,
                    path.ifEmpty { diaryModelCache!!.path })
                roomManager.updateDiary(diaryModel)
                showToast(R.string.txt_create_success)
                finish()
            }
        }
    }

    override fun onBackPressed() {
        val file = File(path)
        if (file.exists()) file.delete()
        super.onBackPressed()
    }

    override fun destroyUI() {
        takePhotoForResult.unregister()
    }

    override fun onDateAllow(key: String, date: String) {
        viewBinding.tvSelectDate.text = date
    }

}