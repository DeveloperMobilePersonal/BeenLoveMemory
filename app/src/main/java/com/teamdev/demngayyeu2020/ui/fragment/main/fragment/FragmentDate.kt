package com.teamdev.demngayyeu2020.ui.fragment.main.fragment

import android.os.Handler
import android.os.Looper
import com.teamdev.demngayyeu2020.R
import com.teamdev.demngayyeu2020.base.BaseFragment
import com.teamdev.demngayyeu2020.databinding.FragmentDateBinding
import com.teamdev.demngayyeu2020.databinding.FragmentDiaryBinding
import com.teamdev.demngayyeu2020.databinding.FragmentMainBinding
import com.teamdev.demngayyeu2020.databinding.FragmentWaveBinding
import com.teamdev.demngayyeu2020.dialog.input.InputListener
import com.teamdev.demngayyeu2020.dialog.menu.letter.LetterListener
import com.teamdev.demngayyeu2020.ex.*
import com.teamdev.demngayyeu2020.ui.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FragmentDate : BaseFragment<FragmentDateBinding>(), LetterListener, InputListener {

    private val mainViewModel: MainViewModel by sharedViewModel()
    private val handler: Handler by lazy { Handler(Looper.getMainLooper()) }
    private val runnable = Runnable {
        if (Pref.loveDay.isNotEmpty()) {
            val dateLoveDetails = getDateLoveDetails(Pref.loveDay)
            val timeCurrent = getTimeCurrent()
            if (isActive()) {
                val message = "${getStringCompat(R.string.txt_message_clock)} " +
                        "${dateLoveDetails.year} ${getStringCompat(R.string.txt_year).lowercase()}, " +
                        "${dateLoveDetails.month} ${getStringCompat(R.string.txt_month).lowercase()}, " +
                        "${dateLoveDetails.week} ${getStringCompat(R.string.txt_week).lowercase()}, " +
                        "${dateLoveDetails.day} ${getStringCompat(R.string.txt_day).lowercase()}, " +
                        "${timeCurrent.first} ${getStringCompat(R.string.txt_hours)}, " +
                        "${timeCurrent.second} ${getStringCompat(R.string.txt_minute)}, " +
                        "${timeCurrent.third} ${getStringCompat(R.string.txt_second)}.(${Pref.loveDay})"
                viewBinding.tvMessageDefault.text = message
            }
        }
        startMessage()
    }

    override fun loadUI(): Int {
        return R.layout.fragment_date
    }

    override fun createUI() {
        runMainActivity { activity ->
            viewBinding.viewModel = mainViewModel
            viewBinding.llLetter.click {
                activity.dialogMenuLetter.showUI(KEY_LETTER, this)
            }
        }
    }

    override fun onResume() {
        startMessage(0)
        super.onResume()
    }

    override fun onPause() {
        stopMessage()
        super.onPause()
    }

    override fun destroyUI() {

    }

    private fun startMessage(delay: Long = 1000) {
        stopMessage()
        handler.postDelayed(runnable, delay)
    }

    private fun stopMessage() {
        handler.removeCallbacks(runnable)
        handler.removeCallbacksAndMessages(null)
    }

    override fun menuLetterEdit(key: String) {
        if (!isActive()) return
        runMainActivity {
            it.dialogInput.showUI(key, this)
        }
    }

    override fun menuLetterRotate(key: String) {
        if (!isActive()) return
        Pref.letterNameLocation = !Pref.letterNameLocation
        mainViewModel.liveLetterNameLocation.value = Pref.letterNameLocation
    }

    override fun onInputAllow(key: String, txt: String) {
        if (!isActive()) return
        if (key == KEY_LETTER) {
            Pref.letterMessageCustom = txt
            mainViewModel.liveHasLetterMessageCustom.value = Pref.letterMessageCustom.isNotEmpty()
            mainViewModel.liveLetterMessageCustom.value = txt
        }
    }

}