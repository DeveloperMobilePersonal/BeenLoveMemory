package com.teamdev.demngayyeu2020.ui.crop

import android.content.Intent
import androidx.lifecycle.lifecycleScope
import com.teamdev.demngayyeu2020.R
import com.teamdev.demngayyeu2020.base.BaseActivity
import com.teamdev.demngayyeu2020.databinding.ActivityGalleryBinding
import com.teamdev.demngayyeu2020.ex.*
import com.teamdev.demngayyeu2020.permission.PermissionManager
import com.teamdev.demngayyeu2020.scan.PhotoModel
import com.teamdev.demngayyeu2020.scan.ScanPhoto
import com.teamdev.demngayyeu2020.ui.crop.adapter.GalleryAdapter
import com.teamdev.demngayyeu2020.ui.crop.adapter.GalleryListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

class GalleryActivity : BaseActivity<ActivityGalleryBinding>(), PermissionManager.PerResult,
    GalleryListener {

    private val galleryAdapter by inject<GalleryAdapter>()
    private val scanPhoto by inject<ScanPhoto>()
    private val permissionManager by inject<PermissionManager>()

    override fun loadUI(): Int {
        return R.layout.activity_gallery
    }

    override fun createUI() {
        permissionManager.addListener(this)
        galleryAdapter.addListener(this)
        viewBinding.ivBack.click {
            setResult(RESULT_CANCELED)
            finish()
        }
        viewBinding.btnGrant.click {
            if (permissionManager.hasPermission()) {
                onAllowPer()
            } else if (permissionManager.hasDeniedPer()) {
                permissionManager.requestPermission()
            } else if (permissionManager.hasAskAgainPer()) {
                permissionManager.openAppSettings()
            }
        }
        viewBinding.recyclerView.disableAnimator()
        viewBinding.recyclerView.spacing(com.intuit.sdp.R.dimen._1sdp)
        viewBinding.recyclerView.gridLayoutManager(galleryAdapter, 3)
        permissionManager.requestPermission()
    }

    override fun destroyUI() {
        scanPhoto.stopScan()
    }

    override fun onBackPressed() {
        setResult(RESULT_CANCELED)
        super.onBackPressed()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (isActive()) {
            permissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onAllowPer() {
        if (!isActive()) return
        viewBinding.crPermissionError.gone()
        lifecycleScope.launch(Dispatchers.IO) {
            val startScan = scanPhoto.startScan()
            withContext(Dispatchers.Main) {
                if (isActive()) {
                    viewBinding.progressBar.gone()
                    galleryAdapter.addList(startScan)
                }
            }
        }
    }

    override fun onDeniedPer() {
        if (!isActive()) return
        viewBinding.crPermissionError.show()
    }

    override fun onAskAgainPer() {
        if (!isActive()) return
        viewBinding.crPermissionError.show()
    }

    override fun onGalleryItem(photoModel: PhotoModel) {
        val intent = Intent()
        intent.putExtra(CropActivity.ID, photoModel.id)
        setResult(RESULT_OK, intent)
        finish()
    }

}