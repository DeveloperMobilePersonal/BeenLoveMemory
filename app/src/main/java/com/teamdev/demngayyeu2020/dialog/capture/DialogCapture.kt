package com.teamdev.demngayyeu2020.dialog.capture

import android.view.Gravity
import androidx.core.app.ShareCompat
import com.teamdev.demngayyeu2020.R
import com.teamdev.demngayyeu2020.base.BaseActivity
import com.teamdev.demngayyeu2020.base.BaseDialog
import com.teamdev.demngayyeu2020.databinding.DialogCaptureBinding
import com.teamdev.demngayyeu2020.ex.click
import com.teamdev.demngayyeu2020.ex.getUriFromFile
import com.teamdev.demngayyeu2020.ex.loadCacheAll
import com.teamdev.demngayyeu2020.ex.loadCacheNone
import java.io.File
import java.net.URLConnection

class DialogCapture(private val activityCompat: BaseActivity<*>) :
    BaseDialog<DialogCaptureBinding>(activityCompat) {
    private var path = ""
    override fun layout(): Int {
        return R.layout.dialog_capture
    }

    override fun runUI() {
        viewBinding.btnCancel.click {
            hideUI()
        }
        viewBinding.btnAllow.click {
            val file = File(path)
            if (!file.exists() || file.isDirectory) return@click
            ShareCompat.IntentBuilder(activityCompat)
                .setStream(file.getUriFromFile(activityCompat))
                .setType(URLConnection.guessContentTypeFromName(file.name))
                .startChooser()
            hideUI()
        }
    }

    fun showUI(path: String) {
        this.path = path
        super.showUI()
        viewBinding.ivPreview.loadCacheNone(path)
    }

    override fun gravity(): Int {
        return Gravity.CENTER
    }
}