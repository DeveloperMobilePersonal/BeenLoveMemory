package com.teamdev.demngayyeu2020.permission

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class PermissionManager(private val activity: Activity) {

    private val code = 101
    private var perResult: PerResult? = null
    private var hasDeniedPer = false
    private var hasAskAgainPer = false

    fun addListener(perResult: PerResult) {
        this.perResult = perResult
    }

    fun hasDeniedPer():Boolean{
        return hasDeniedPer
    }

    fun hasAskAgainPer():Boolean{
        return hasAskAgainPer
    }

    fun requestPermission() {
        if (hasPermission()) {
            perResult?.onAllowPer()
            return
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(READ_EXTERNAL_STORAGE),
                code
            )
        } else {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE),
                code
            )
        }
    }

    fun hasPermission() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        isPermissions(READ_EXTERNAL_STORAGE)
    } else {
        isPermissions(READ_EXTERNAL_STORAGE) && isPermissions(WRITE_EXTERNAL_STORAGE)
    }

    private fun isPermissions(permission: String): Boolean {
        val result = ContextCompat.checkSelfPermission(activity, permission)
        return result == PackageManager.PERMISSION_GRANTED
    }

    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>?,
        grantResults: IntArray?
    ) {
        permissions ?: return
        grantResults ?: return
        when (requestCode) {
            code -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                perResult?.onAllowPer()
            } else {
                var result = true
                for (s in permissions) {
                    result =
                        ActivityCompat.shouldShowRequestPermissionRationale(activity, s)
                    if (!result) {
                        break
                    }
                }
                if (result) {
                    hasDeniedPer = true
                    hasAskAgainPer = false
                    perResult?.onDeniedPer()
                } else {
                    hasDeniedPer = false
                    hasAskAgainPer = true
                    perResult?.onAskAgainPer()
                }
            }
        }
    }

    interface PerResult {
        fun onAllowPer()
        fun onDeniedPer()
        fun onAskAgainPer()
    }

    fun openAppSettings() {
        val myAppSettings = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.parse("package:" + activity.packageName)
        )
        myAppSettings.addCategory(Intent.CATEGORY_DEFAULT)
        myAppSettings.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        activity.startActivity(myAppSettings)
    }
}