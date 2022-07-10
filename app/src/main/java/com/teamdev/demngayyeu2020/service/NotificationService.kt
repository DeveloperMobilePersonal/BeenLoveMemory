package com.teamdev.demngayyeu2020.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import com.teamdev.demngayyeu2020.ex.Pref
import com.teamdev.demngayyeu2020.ex.showLog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.scope.AndroidScopeComponent
import org.koin.android.scope.serviceScope
import org.koin.core.scope.Scope

class NotificationService : Service(),
    AndroidScopeComponent {

    companion object {
        fun startOrStop(context: Context, isStop: Boolean = true) {
            val intent = Intent(context, NotificationService::class.java)
            if (Pref.isNotification) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(intent)
                } else context.startService(intent)
            } else if (isStop) {
                context.stopService(intent)
            }
        }
    }

    override val scope: Scope by serviceScope()

    private val notificationHelper by inject<NotificationHelper>()
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        notificationHelper.createChannel()
        startForeground(12, notificationHelper.notificationFake())
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        coroutineScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                val notification = notificationHelper.notification()
                startForeground(12, notification)
            }.getOrElse {
                startForeground(12, notificationHelper.notificationFake())
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        coroutineScope.cancel()
        stopForeground(true)
        super.onDestroy()
    }
}