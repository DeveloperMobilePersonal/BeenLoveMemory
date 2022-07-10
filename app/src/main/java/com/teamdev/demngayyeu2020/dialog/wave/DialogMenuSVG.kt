package com.teamdev.demngayyeu2020.dialog.wave

import android.os.Handler
import android.os.Looper
import android.view.Gravity
import com.teamdev.demngayyeu2020.R
import com.teamdev.demngayyeu2020.base.BaseActivity
import com.teamdev.demngayyeu2020.base.BaseDialog
import com.teamdev.demngayyeu2020.databinding.DialogMenuSvgBinding
import com.teamdev.demngayyeu2020.dialog.wave.adapter.SVGAdapter
import com.teamdev.demngayyeu2020.ex.*
import com.teamdev.demngayyeu2020.viewanimator.ViewAnimator

class DialogMenuSVG(activityCompat: BaseActivity<*>, private val adapter: SVGAdapter) :
    BaseDialog<DialogMenuSvgBinding>(activityCompat), SVGListener {

    private var listener: SVGListener? = null
    private var key = ""

    override fun layout(): Int {
        return R.layout.dialog_menu_svg
    }

    override fun runUI() {
        viewBinding.grView.adapter = adapter
        viewBinding.ivClose.click {
            hideUI()
        }
    }

    override fun animate(): Int {
        return -1
    }

    override fun gravity(): Int {
        return Gravity.BOTTOM
    }

    fun showUI(key: String, currentList: MutableList<SVGModel>, listener: SVGListener) {
        this.listener = listener
        this.key = key
        super.showUI()
        when(key){
            KEY_WAVE_SVG->viewBinding.tvTitle.text = getStringCompat(R.string.txt_edit_wave)
            else ->viewBinding.tvTitle.text = getStringCompat(R.string.txt_edit_face_frame)
        }
        ViewAnimator.animate(viewBinding.root).slideBottomInNoneAlpha().duration(250).start()
        handler?.removeCallbacksAndMessages(null)
        if (adapter.currentList == currentList) {
            viewBinding.progressBar.gone()
            return
        }
        adapter.reloadList(mutableListOf(), this)
        viewBinding.progressBar.show()
        if (handler == null) {
            handler = Handler(Looper.getMainLooper())
        }
        handler?.postDelayed({
            viewBinding.progressBar.gone()
            adapter.reloadList(currentList, this)
        }, 500)
    }

    override fun itemSVGAllow(key: String, id: Int) {
        listener?.itemSVGAllow(this.key, id)
        hideUI()
    }

}