package com.teamdev.demngayyeu2020.ui.splash

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.window.SplashScreenView

class NActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        overridePendingTransition(0, 0)
        val intentStart: Intent = packageManager.getLaunchIntentForPackage(packageName)
            ?: Intent(this, SplashActivity::class.java)
        intentStart.apply {
            flags = (Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(this)
        }
        finish()
    }
}