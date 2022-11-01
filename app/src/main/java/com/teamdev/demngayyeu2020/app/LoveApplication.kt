package com.teamdev.demngayyeu2020.app

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDex
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.firebase.messaging.FirebaseMessaging
import com.jakewharton.threetenabp.AndroidThreeTen
import com.teamdev.demngayyeu2020.ex.Pref
import com.teamdev.demngayyeu2020.module.listModule
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin

class LoveApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        AndroidThreeTen.init(this)
        ContextApplication.set(this)
        Pref.load(this)
        startKoin {
            androidContext(applicationContext)
            fragmentFactory()
            modules(listModule)
        }
        val builder = RequestConfiguration.Builder()
        builder.setTestDeviceIds(listOf("6F7ADC4FD592DEA0C235BC3A2CCEC2EC"))
        MobileAds.setRequestConfiguration(builder.build())
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}