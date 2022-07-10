package com.teamdev.demngayyeu2020.ui.crop

import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import com.teamdev.demngayyeu2020.R
import com.teamdev.demngayyeu2020.base.BaseActivity
import com.teamdev.demngayyeu2020.databinding.ActivityCropBinding
import com.teamdev.demngayyeu2020.ex.*
import com.teamdev.demngayyeu2020.viewanimator.ViewAnimator
import com.theartofdev.edmodo.cropper.CropImageView
import java.io.File

class CropActivity : BaseActivity<ActivityCropBinding>(),
    CropImageView.OnCropImageCompleteListener,
    CropImageView.OnSetImageUriCompleteListener {

    companion object {
        const val CROP = "crop"
        const val PATH = "path"
        const val ID = "id"
    }

    private var path = ""

    override fun loadUI(): Int {
        return R.layout.activity_crop
    }

    override fun createUI() {
        when (intent.getStringExtra(CROP) ?: finish()) {
            KEY_FACE_MALE, KEY_FACE_FEMALE -> {
                viewBinding.cropImageView.setAspectRatio(1, 1)
            }
        }
        viewBinding.cropImageView.setOnCropImageCompleteListener(this)
        viewBinding.cropImageView.setOnSetImageUriCompleteListener(this)
        viewBinding.cropImageView.isShowProgressBar = true
        registerGallery()
        viewBinding.ivDone.click {
            viewBinding.progressBar.show()
            viewBinding.ivDone.hide()
            val key = intent.getStringExtra(CROP).toString()
            path = if (key == KEY_DIARY) {
                getPathDiary(key)
            } else {
                getPathFace(key)
            }
            val path = File(path).toUri()
            viewBinding.cropImageView.saveCroppedImageAsync(path)
        }
        viewBinding.ivBack.click {
            setResult(RESULT_CANCELED)
            finish()
        }
    }

    override fun destroyUI() {
        viewBinding.cropImageView.setOnCropImageCompleteListener(null)
        viewBinding.cropImageView.setOnSetImageUriCompleteListener(null)
        galleryActivityLauncher.unregister()
    }

    private val galleryActivityLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (isActive()) {
                if (it == null) {
                    finish()
                } else {
                    val data = it.data
                    if (it.resultCode == RESULT_OK && data != null) {
                        val uri: Uri = MediaStore.Files.getContentUri("external")
                        val longExtra = data.getLongExtra(ID, -1)
                        val uriPhoto = Uri.withAppendedPath(uri, longExtra.toString())
                        viewBinding.cropImageView.setImageUriAsync(uriPhoto)
                    } else {
                        finish()
                    }
                }
            }
        }

    private fun registerGallery() {
        val intent = Intent(this, GalleryActivity::class.java)
        galleryActivityLauncher.launch(intent)
    }

    override fun onCropImageComplete(view: CropImageView?, result: CropImageView.CropResult?) {
        if (!isActive()) return
        if (result == null || !result.isSuccessful) {
            showToast(R.string.txt_error)
            finish()
            return
        }
        val key = intent.getStringExtra(CROP).toString()
        val intent = Intent()
        intent.putExtra(CROP, key)
        intent.putExtra(PATH, path)
        setResult(RESULT_OK, intent)
        finish()
    }

    override fun onSetImageUriComplete(view: CropImageView?, uri: Uri?, error: Exception?) {
        if (!isActive()) return
        if (error != null || uri == null) {
            showToast(R.string.txt_error)
            registerGallery()
        } else {
            viewBinding.ivDone.show()
            ViewAnimator.animate(viewBinding.ivDone).bounceIn().duration(500).start()
        }
    }
}