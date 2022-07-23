package com.teamdev.demngayyeu2020.ui.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.drawToBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.teamdev.demngayyeu2020.R
import com.teamdev.demngayyeu2020.app.ContextApplication
import com.teamdev.demngayyeu2020.base.BaseActivity
import com.teamdev.demngayyeu2020.databinding.ActivityMainBinding
import com.teamdev.demngayyeu2020.dialog.capture.DialogCapture
import com.teamdev.demngayyeu2020.dialog.color.DialogColor
import com.teamdev.demngayyeu2020.dialog.date.DialogDate
import com.teamdev.demngayyeu2020.dialog.input.DialogInput
import com.teamdev.demngayyeu2020.dialog.menu.birthday.DialogMenuBirthday
import com.teamdev.demngayyeu2020.dialog.menu.diary.DialogMenuDiary
import com.teamdev.demngayyeu2020.dialog.menu.info.DialogMenuInfo
import com.teamdev.demngayyeu2020.dialog.menu.letter.DialogMenuLetter
import com.teamdev.demngayyeu2020.dialog.menu.wave.DialogMenuWithWave
import com.teamdev.demngayyeu2020.dialog.rate.DialogRate
import com.teamdev.demngayyeu2020.dialog.wave.DialogMenuSVG
import com.teamdev.demngayyeu2020.ex.*
import com.teamdev.demngayyeu2020.room.RoomManager
import com.teamdev.demngayyeu2020.service.NotificationService
import com.teamdev.demngayyeu2020.ui.adapter.ViewPagerAdapter
import com.teamdev.demngayyeu2020.ui.crop.CropActivity
import com.teamdev.demngayyeu2020.ui.fragment.diary.FragmentDiary
import com.teamdev.demngayyeu2020.ui.fragment.diary.adapter.DiaryAdapter
import com.teamdev.demngayyeu2020.ui.fragment.main.FragmentMain
import com.teamdev.demngayyeu2020.ui.fragment.main.fragment.FragmentDate
import com.teamdev.demngayyeu2020.ui.fragment.main.fragment.FragmentWave
import com.teamdev.demngayyeu2020.ui.fragment.setting.FragmentSetting
import com.teamdev.demngayyeu2020.unit.scrMainBanner
import com.teamdev.demngayyeu2020.viewanimator.ViewAnimator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import kotlin.math.abs
import kotlin.math.max

class MainActivity : BaseActivity<ActivityMainBinding>() {

    companion object {
        fun open(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
            if (context is Activity) {
                context.finish()
            }
        }
    }

    private val takePhotoForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK && isActive()) {
                val intent = result.data ?: return@registerForActivityResult
                val key = intent.getStringExtra(CropActivity.CROP)
                val path = intent.getStringExtra(CropActivity.PATH) ?: ""
                when (key) {
                    KEY_FACE_MALE, KEY_NAME_MALE -> {
                        Pref.faceMale = path
                        mainViewModel.liveFaceMale.value = Pref.faceMale
                        NotificationService.startOrStop(ContextApplication.get(), false)
                    }
                    KEY_FACE_FEMALE, KEY_NAME_FEMALE -> {
                        Pref.faceFeMale = path
                        mainViewModel.liveFaceFeMale.value = Pref.faceFeMale
                        NotificationService.startOrStop(ContextApplication.get(), false)
                    }
                    KEY_BACKGROUND -> {
                        Pref.background = path
                        mainViewModel.liveBackground.value = Pref.background
                    }
                }
            }
        }

    private val mainViewModel: MainViewModel by viewModel()
    val roomManager: RoomManager by inject()
    private val viewPagerAdapter: ViewPagerAdapter by inject {
        parametersOf(
            supportFragmentManager,
            lifecycle
        )
    }
    val viewPagerAdapter2: ViewPagerAdapter by inject {
        parametersOf(
            supportFragmentManager,
            lifecycle
        )
    }
    val dialogMenuWithWave: DialogMenuWithWave by inject()
    val dialogInput: DialogInput by inject()
    val dialogDate: DialogDate by inject()
    val dialogColor: DialogColor by inject()
    val dialogMenuBirthday: DialogMenuBirthday by inject()
    val dialogMenuInfo: DialogMenuInfo by inject()
    val dialogMenuSVG: DialogMenuSVG by inject()
    val dialogMenuLetter: DialogMenuLetter by inject()
    val diaryAdapter by inject<DiaryAdapter>()
    val diaryMenuDiary by inject<DialogMenuDiary>()
    private val dialogCapture by inject<DialogCapture>()
    private val dialogRate by inject<DialogRate>()
    private val fragmentWave: FragmentWave by inject()
    private val fragmentDate: FragmentDate by inject()
    private val fragmentMain: FragmentMain by inject()
    private val fragmentDiary: FragmentDiary by inject()
    private val fragmentSetting: FragmentSetting by inject()
    private val minScale = 0.85f
    private val minAlpha = 0.5f

    override fun isStatusTransparent(): Boolean {
        return true
    }

    override fun loadUI(): Int {
        return R.layout.activity_main
    }

    override fun createUI() {
        loadBanner()
        viewBinding.viewModel = mainViewModel
        viewBinding.ivDiary.loadCacheAll(R.drawable.ic_diary)
        viewBinding.ivSetting.loadCacheAll(R.drawable.ic_setting)
        viewBinding.ivDiary.click {
            diarySelect()
        }
        viewBinding.tvHome.click {
            homeSelect()
        }
        viewBinding.ivSetting.click {
            settingSelect()
        }
        viewPagerAdapter.fragmets = listOf<Fragment>(fragmentDiary, fragmentMain, fragmentSetting)
        viewPagerAdapter2.fragmets = listOf<Fragment>(fragmentWave, fragmentDate)
        viewBinding.viewPager.adapter = viewPagerAdapter
        viewBinding.viewPager.setCurrentItem(1, false)
        viewBinding.viewPager.offscreenPageLimit = viewPagerAdapter.fragmets.size
        viewBinding.viewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> diarySelect()
                    1 -> homeSelect()
                    2 -> settingSelect()
                }
            }
        })
        viewBinding.viewPager.setPageTransformer { page, position ->
            val width = page.width
            val height = page.height
            if (position < -1) {
                page.alpha = 0f
            } else if (position <= 1) {
                val scaleFactor = max(minScale, 1 - abs(position))
                val vertMargin = height * (1 - scaleFactor) / 2
                val horzMargin = width * (1 - scaleFactor) / 2
                if (position < 0) {
                    page.translationX = horzMargin - vertMargin / 2
                } else {
                    page.translationX = -horzMargin + vertMargin / 2
                }
                page.scaleX = scaleFactor
                page.scaleY = scaleFactor
                page.alpha = minAlpha +
                        (scaleFactor - minScale) /
                        (1 - minScale) * (1 - minAlpha)
            } else {
                page.alpha = 0f
            }
        }
    }

    private fun settingSelect() {
        viewBinding.viewAlpha.show()
        ViewAnimator.animate(viewBinding.viewAlpha).fadeIn().duration(350).start()
        viewBinding.tvHome.alpha = 0.6f
        viewBinding.ivDiary.alpha = 0.6f
        viewBinding.ivSetting.alpha = 1f
        viewBinding.viewPager.currentItem = 2
    }

    private fun homeSelect() {
        ViewAnimator.animate(viewBinding.viewAlpha).fadeOut().onStop {
            if (isActive()) {
                viewBinding.viewAlpha.gone()
            }
        }.duration(350).start()
        viewBinding.tvHome.alpha = 1f
        viewBinding.ivDiary.alpha = 0.6f
        viewBinding.ivSetting.alpha = 0.6f
        viewBinding.viewPager.currentItem = 1
    }

    private fun diarySelect() {
        viewBinding.viewAlpha.show()
        ViewAnimator.animate(viewBinding.viewAlpha).fadeIn().duration(350).start()
        viewBinding.tvHome.alpha = 0.6f
        viewBinding.ivDiary.alpha = 1f
        viewBinding.ivSetting.alpha = 0.6f
        viewBinding.viewPager.currentItem = 0
    }

    override fun onBackPressed() {
        if (dialogRate.isShowing) {
            super.onBackPressed()
        } else {
            dialogRate.showUI()
        }
    }

    override fun destroyUI() {
        takePhotoForResult.unregister()
    }

    fun openCropImage(key: String) {
        val intent = Intent(this, CropActivity::class.java)
        intent.putExtra(CropActivity.CROP, key)
        takePhotoForResult.launch(intent)
    }

    private fun loadBanner() {
        val adView = AdView(this)
        viewBinding.frameBannerAds.removeAllViews()
        viewBinding.frameBannerAds.addView(adView)
        adView.adUnitId = scrMainBanner()
        adView.adSize = adSize
        adView.loadObserver {
            if (isActive() && it.not()) {
                viewBinding.frameBannerAds.visibility = View.GONE
            }
        }
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }

    fun captureScreen() {
        lifecycleScope.launch(Dispatchers.Main) {
            kotlin.runCatching {
                val drawToBitmap = viewBinding.csContent.drawToBitmap()
                saveBitmap(drawToBitmap) { path ->
                    if (isActive() && path.isNotEmpty()) {
                        dialogCapture.showUI(path)
                    }
                }
            }
        }
    }

    private val adSize: AdSize
        get() {
            val outMetrics = DisplayMetrics()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                val display = display
                display?.getRealMetrics(outMetrics)
            } else {
                @Suppress("DEPRECATION")
                val display = windowManager.defaultDisplay
                @Suppress("DEPRECATION")
                display.getMetrics(outMetrics)
            }

            val density = outMetrics.density

            var adWidthPixels = viewBinding.frameBannerAds.width.toFloat()
            if (adWidthPixels == 0f) {
                adWidthPixels = outMetrics.widthPixels.toFloat()
            }

            val adWidth = (adWidthPixels / density).toInt()
            return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth)
        }
}