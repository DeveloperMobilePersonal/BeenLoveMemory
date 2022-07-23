package com.teamdev.demngayyeu2020.service

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.Icon
import android.os.Build
import android.widget.RemoteViews
import androidx.annotation.MainThread
import androidx.annotation.UiThread
import androidx.annotation.WorkerThread
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.teamdev.demngayyeu2020.R
import com.teamdev.demngayyeu2020.ex.Pref
import com.teamdev.demngayyeu2020.ex.getLoveDay
import com.teamdev.demngayyeu2020.ex.getStringCompat
import com.teamdev.demngayyeu2020.ex.loadBitmapCircleCrop
import com.teamdev.demngayyeu2020.ui.main.MainActivity
import com.teamdev.demngayyeu2020.ui.splash.NActivity
import java.io.File


class NotificationHelper(context: Context) : ContextWrapper(context) {

    private val mManager: NotificationManager by lazy { getSystemService(NOTIFICATION_SERVICE) as NotificationManager }

    fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel("Love", "Love", NotificationManager.IMPORTANCE_LOW).apply {
                    description = getStringCompat(R.string.app_name)
                    enableLights(false)
                    enableVibration(false)
                    setShowBadge(false)
                }
            mManager.createNotificationChannel(channel)
        }
    }

    @SuppressLint("RemoteViewLayout", "RestrictedApi")
    @UiThread
    fun notification(): Notification {
        val notificationLayout = RemoteViews(packageName, R.layout.notification)
        /*val notificationIntent = Intent(this, MainActivity::class.java)
        notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            notificationIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )*/
        val builder = NotificationCompat.Builder(applicationContext, "Love")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(false)
            .setOngoing(true)
            .setCustomContentView(notificationLayout)
        builder.contentView.setTextViewText(R.id.tvLoveSum, getLoveDay(Pref.loveDay))
        if (Pref.sexLocation) {
            //builder.contentView.setTextViewText(R.id.txt_1, Pref.nameMale)
            // builder.contentView.setTextViewText(R.id.txt_2, Pref.nameFeMale)
            if (Pref.faceMale.isNotEmpty()) {
                val bitmap = loadBitmapCircleCrop(Pref.faceMale, 128)
                builder.contentView.setImageViewBitmap(R.id.iv1, bitmap)
            } else {
                builder.contentView.setImageViewResource(
                    R.id.iv1,
                    R.drawable.ic_male_default_notification
                )
            }
            if (Pref.faceFeMale.isNotEmpty()) {
                val bitmap = loadBitmapCircleCrop(Pref.faceFeMale, 128)
                builder.contentView.setImageViewBitmap(R.id.iv2, bitmap)
            } else {
                builder.contentView.setImageViewResource(
                    R.id.iv2,
                    R.drawable.ic_female_default_notification
                )
            }
        } else {
            //  builder.contentView.setTextViewText(R.id.txt_1, Pref.nameFeMale)
            //  builder.contentView.setTextViewText(R.id.txt_2, Pref.nameMale)
            if (Pref.faceMale.isNotEmpty()) {
                val bitmap = loadBitmapCircleCrop(Pref.faceMale, 128)
                builder.contentView.setImageViewBitmap(R.id.iv2, bitmap)
            } else {
                builder.contentView.setImageViewResource(R.id.iv2, R.drawable.ic_male_default)
            }
            if (Pref.faceFeMale.isNotEmpty()) {
                val bitmap = loadBitmapCircleCrop(Pref.faceMale, 128)
                builder.contentView.setImageViewBitmap(R.id.iv1, bitmap)
            } else {
                builder.contentView.setImageViewResource(R.id.iv1, R.drawable.ic_female_default)
            }
        }
        return builder.build()
    }

    @SuppressLint("RemoteViewLayout", "RestrictedApi")
    fun notificationFake(): Notification {
        val notificationIntent = Intent(this, NActivity::class.java)
        notificationIntent.flags =
            Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            notificationIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val builder = NotificationCompat.Builder(applicationContext, "Love")
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(false)
            .setShowWhen(false)
            .setOngoing(true)
        return builder.build()
    }

}