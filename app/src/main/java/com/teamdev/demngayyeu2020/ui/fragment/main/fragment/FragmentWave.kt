package com.teamdev.demngayyeu2020.ui.fragment.main.fragment

import com.teamdev.demngayyeu2020.R
import com.teamdev.demngayyeu2020.base.BaseFragment
import com.teamdev.demngayyeu2020.databinding.FragmentWaveBinding
import com.teamdev.demngayyeu2020.dialog.color.ColorListener
import com.teamdev.demngayyeu2020.dialog.date.DateListener
import com.teamdev.demngayyeu2020.dialog.input.InputListener
import com.teamdev.demngayyeu2020.dialog.menu.wave.MenuWithWaveListener
import com.teamdev.demngayyeu2020.dialog.wave.SVGListener
import com.teamdev.demngayyeu2020.ex.*
import com.teamdev.demngayyeu2020.ui.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FragmentWave : BaseFragment<FragmentWaveBinding>(),
    MenuWithWaveListener,
    InputListener,
    DateListener,
    ColorListener,
    SVGListener {

    private val mainViewModel: MainViewModel by sharedViewModel()

    override fun loadUI(): Int {
        return R.layout.fragment_wave
    }

    override fun createUI() {
        runMainActivity { activity ->
            viewBinding.viewModel = mainViewModel
            activity.dialogMenuWithWave.addListener(this)
            viewBinding.wave.click {
                activity.dialogMenuWithWave.showUI()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        showLog(javaClass, "pauseAnimation")
        viewBinding.wave.pauseAnimation()
    }

    override fun onResume() {
        super.onResume()
        viewBinding.wave.resumeAnimation()
    }

    override fun destroyUI() {

    }

    override fun menuWithWaveEditStatusTop() {
        runMainActivity {
            it.dialogInput.showUI(KEY_TXT_TOP, this)
        }
    }

    override fun menuWithWaveEditStatusBottom() {
        runMainActivity {
            it.dialogInput.showUI(KEY_TXT_BOTTOM, this)
        }
    }

    override fun menuWithWaveEditWave() {
        runMainActivity { activity ->
            activity.dialogMenuSVG.showUI(KEY_WAVE_SVG, waveSVGList, this)
        }
    }

    override fun menuWithWaveEditWaveColor() {
        runMainActivity { activity ->
            activity.dialogColor.showUI(KEY_WAVE_COLOR, this)
        }
    }

    override fun menuWithWaveEditDateLove() {
        runMainActivity { activity ->
            activity.dialogDate.showUI(KEY_LOVE_DAY, this)
        }
    }

    override fun menuWithWaveEditWallpaper() {
        runMainActivity { activity ->
            activity.openCropImage(KEY_BACKGROUND)
        }
    }

    override fun menuWithWaveCapture() {

    }

    override fun onInputAllow(key: String, txt: String) {
        if (!isActive()) return
        when (key) {
            KEY_TXT_TOP -> {
                Pref.txtTop = txt
                mainViewModel.liveStatusTop.value = Pref.txtTop
            }
            KEY_TXT_BOTTOM -> {
                Pref.txtBottom = txt
                mainViewModel.liveStatusBottom.value = Pref.txtBottom
            }
        }
    }

    override fun onDateAllow(key: String, date: String) {
        if (!isActive()) return
        if (key == KEY_LOVE_DAY) {
            Pref.loveDay = date
            mainViewModel.postLoveDay()
        }
    }

    override fun onAllowColor(key: String, color: Int) {
        if (!isActive()) return
        if (key == KEY_WAVE_COLOR) {
            Pref.waveColor = color
            viewBinding.wave.updateColor(color)
        }
    }

    override fun itemSVGAllow(key: String, id: Int) {
        if (key == KEY_WAVE_SVG) {
            Pref.idWaveSvg = id
            viewBinding.wave.invalidate()
        }
    }
}