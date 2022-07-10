package com.teamdev.demngayyeu2020.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

@SuppressLint("StaticFieldLeak")
object ContextApplication {

    private lateinit var contextCache: Context

    fun set(application: Application) {
        contextCache = application
    }

    fun get(): Context {
        return contextCache
    }
}