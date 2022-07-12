package com.teamdev.demngayyeu2020.ui.fragment.main

import com.teamdev.demngayyeu2020.R
import com.teamdev.demngayyeu2020.app.ContextApplication
import com.teamdev.demngayyeu2020.base.BaseFragment
import com.teamdev.demngayyeu2020.databinding.FragmentMainBinding
import com.teamdev.demngayyeu2020.dialog.color.ColorListener
import com.teamdev.demngayyeu2020.dialog.date.DateListener
import com.teamdev.demngayyeu2020.dialog.input.InputListener
import com.teamdev.demngayyeu2020.dialog.menu.birthday.MenuBirthdayListener
import com.teamdev.demngayyeu2020.dialog.menu.info.MenuInfoListener
import com.teamdev.demngayyeu2020.dialog.wave.SVGListener
import com.teamdev.demngayyeu2020.ex.*
import com.teamdev.demngayyeu2020.service.NotificationService
import com.teamdev.demngayyeu2020.ui.main.MainViewModel
import com.teamdev.demngayyeu2020.viewanimator.ViewAnimator
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FragmentMain : BaseFragment<FragmentMainBinding>(),
    InputListener,
    DateListener,
    ColorListener,
    MenuInfoListener,
    MenuBirthdayListener,
    SVGListener {

    private val mainViewModel: MainViewModel by sharedViewModel()

    override fun loadUI(): Int {
        return R.layout.fragment_main
    }

    override fun createUI() {
        runMainActivity { activity ->
            viewBinding.viewModel = mainViewModel
            mainViewModel.postBirthdayMale()
            mainViewModel.postBirthdayFeMale()
            val viewPagerAdapter = activity.viewPagerAdapter2
            viewBinding.viewPager.adapter = viewPagerAdapter
            viewBinding.viewPager.offscreenPageLimit = viewPagerAdapter.fragmets.size
            viewBinding.tvName1.click {
                activity.dialogMenuInfo.showUI(KEY_NAME_MALE, this)
            }
            viewBinding.tvName2.click {
                activity.dialogMenuInfo.showUI(KEY_NAME_FEMALE, this)
            }
            viewBinding.ivFace1.click {
                activity.dialogMenuInfo.showUI(KEY_FACE_MALE, this)
            }
            viewBinding.ivFace2.click {
                activity.dialogMenuInfo.showUI(KEY_FACE_FEMALE, this)
            }
            viewBinding.llAge1.click {
                activity.dialogMenuBirthday.showUI(KEY_AGE_MALE, this)
            }
            viewBinding.llAge2.click {
                activity.dialogMenuBirthday.showUI(KEY_AGE_FEMALE, this)
            }
            viewBinding.llBow1.click {
                activity.dialogMenuBirthday.showUI(KEY_BOW_MALE, this)
            }
            viewBinding.llBow2.click {
                activity.dialogMenuBirthday.showUI(KEY_BOW_FEMALE, this)
            }
            ViewAnimator.animate(viewBinding.ivStatusIcon)
                .scale(0.8f, 1f)
                .duration(250)
                .repeatCount(ViewAnimator.INFINITE)
                .repeatMode(ViewAnimator.REVERSE)
                .start()
            ViewAnimator.animate(viewBinding.root).fadeIn().duration(500).start()
        }
    }

    override fun destroyUI() {

    }

    override fun menuBirthdayEdit(key: String) {
        runMainActivity { activity ->
            when (key) {
                KEY_AGE_MALE, KEY_BOW_MALE -> {
                    activity.dialogDate.showUI(KEY_BIRTHDAY_MALE, this)
                }
                KEY_AGE_FEMALE, KEY_BOW_FEMALE -> {
                    activity.dialogDate.showUI(KEY_BIRTHDAY_FEMALE, this)
                }
            }
        }
    }

    override fun menuBirthdayEditBg(key: String) {
        runMainActivity { activity ->
            activity.dialogColor.showUI(key, this)
        }
    }

    override fun menuBirthdayEditSex() {
        if (!isActive()) return
        Pref.sexLocation = !Pref.sexLocation
        mainViewModel.liveSexLocation.value = Pref.sexLocation
    }

    override fun onInputAllow(key: String, txt: String) {
        if (!isActive()) return
        when (key) {
            KEY_NAME_MALE -> {
                Pref.nameMale = txt
                mainViewModel.liveNameMale.value = Pref.nameMale
                NotificationService.startOrStop(ContextApplication.get(), false)
            }
            KEY_NAME_FEMALE -> {
                Pref.nameFeMale = txt
                mainViewModel.liveNameFemale.value = Pref.nameFeMale
                NotificationService.startOrStop(ContextApplication.get(), false)
            }
        }
    }

    override fun onDateAllow(key: String, date: String) {
        if (!isActive()) return
        when (key) {
            KEY_BIRTHDAY_MALE -> {
                Pref.birthdayMale = date
                mainViewModel.postBirthdayMale()
            }
            KEY_BIRTHDAY_FEMALE -> {
                Pref.birthdayFeMale = date
                mainViewModel.postBirthdayFeMale()
            }
        }
    }

    override fun onAllowColor(key: String, color: Int) {
        if (!isActive()) return
        when (key) {
            KEY_AGE_MALE -> {
                Pref.ageMaleBgColor = color
                mainViewModel.postBgAgeMale()
            }
            KEY_BOW_MALE -> {
                Pref.bowMaleBgColor = color
                mainViewModel.postBgBowMale()
            }
            KEY_AGE_FEMALE -> {
                Pref.ageFeMaleBgColor = color
                mainViewModel.postBgAgeFeMale()
            }
            KEY_BOW_FEMALE -> {
                Pref.bowFeMaleBgColor = color
                mainViewModel.postBgBowFeMale()
            }
        }
    }

    override fun menuInfoEditFace(key: String) {
        runMainActivity { activity ->
            val keyNew = when (key) {
                KEY_NAME_MALE -> KEY_FACE_MALE
                KEY_NAME_FEMALE -> KEY_FACE_FEMALE
                else -> key
            }
            activity.openCropImage(keyNew)
        }
    }

    override fun menuInfoEditFaceFrame(key: String) {
        runMainActivity {
            it.dialogMenuSVG.showUI(key, getListFrame(), this)
        }
    }

    override fun menuInfoEditName(key: String) {
        runMainActivity { activity ->
            val keyNew = when (key) {
                KEY_FACE_MALE -> KEY_NAME_MALE
                KEY_FACE_FEMALE -> KEY_NAME_FEMALE
                else -> key
            }
            activity.dialogInput.showUI(keyNew, this)
        }
    }

    override fun itemSVGAllow(key: String, id: Int) {
        if (!isActive()) return
        when (key) {
            KEY_FACE_MALE -> {
                Pref.idFrameMaleSvg = id
                mainViewModel.liveFrameImageMale.value =
                    getFrameOrDefault(Pref.idFrameMaleSvg).rawSvg
            }
            KEY_FACE_FEMALE -> {
                Pref.idFrameFeMaleSvg = id
                mainViewModel.liveFrameImageFeMale.value =
                    getFrameOrDefault(Pref.idFrameFeMaleSvg).rawSvg
            }
        }
    }
}