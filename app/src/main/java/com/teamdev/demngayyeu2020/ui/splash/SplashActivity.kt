package com.teamdev.demngayyeu2020.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.transition.TransitionManager
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import com.teamdev.demngayyeu2020.R
import com.teamdev.demngayyeu2020.base.BaseActivity
import com.teamdev.demngayyeu2020.databinding.ActivitySplashBinding
import com.teamdev.demngayyeu2020.ex.hide
import com.teamdev.demngayyeu2020.ex.show
import com.teamdev.demngayyeu2020.ui.main.MainActivity
import com.teamdev.demngayyeu2020.unit.scrSplashOpenApp
import com.teamdev.demngayyeu2020.viewanimator.ViewAnimator

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<ActivitySplashBinding>(), JobSplash.JobProgress {

    private var isErrorAdmob = false
    private val jobSplash = JobSplash()
    private var appOpenAd: AppOpenAd? = null

    override fun loadUI(): Int {
        return R.layout.activity_splash
    }

    override fun createUI() {
        loadAdmob()
    }

    override fun onResume() {
        jobSplash.startJob(this)
        super.onResume()
    }

    override fun onPause() {
        jobSplash.stopJob()
        super.onPause()
    }

    override fun destroyUI() {

    }

    override fun isStatusTransparent(): Boolean {
        return true
    }

    override fun onProgress(count: Int) {
        if (!isActive()) {
            return
        }
        if (jobSplash.isShowAds()) {
            return
        }
        viewBinding.progressBar.setProgressCompat(count, true)
        if (count >= 50 && !isLoadAdmob()) {
            jobSplash.setDelay(160)
        }
        if (isLoadAdmob() && !jobSplash.isShowAds()) {
            viewBinding.progressBar.hide()
            viewBinding.tvLoad.hide()
            jobSplash.setShowAds()
            jobSplash.stopJob()
            appOpenAd!!.fullScreenContentCallback = fullScreenContentCallback
            appOpenAd!!.show(this)
        } else if ((!jobSplash.isShowAds() && jobSplash.isProgressMax()) || isErrorAdmob) {
            jobSplash.setShowAds()
            startActivity()
        }
    }

    private fun startActivity() {
        if (!isActive()) {
            return
        }
        jobSplash.stopJob()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun loadAdmob() {
        val request = getAdRequest()
        AppOpenAd.load(
            this,
            scrSplashOpenApp(),
            request,
            AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
            loadCallback
        )
    }

    private fun isLoadAdmob(): Boolean {
        return appOpenAd != null && !isErrorAdmob
    }

    private fun getAdRequest(): AdRequest {
        return AdRequest.Builder().build()
    }

    private val fullScreenContentCallback = object : FullScreenContentCallback() {
        override fun onAdDismissedFullScreenContent() {
            if (isActive()) {
                startActivity()
            }
        }

        override fun onAdFailedToShowFullScreenContent(p0: AdError) {
            if (isActive()) {
                startActivity()
            }
        }
    }

    private val loadCallback = object : AppOpenAd.AppOpenAdLoadCallback() {
        override fun onAdLoaded(p0: AppOpenAd) {
            if (isActive()) {
                isErrorAdmob = false
                appOpenAd = p0
            }
        }

        override fun onAdFailedToLoad(p0: LoadAdError) {
            isErrorAdmob = true
        }
    }
}