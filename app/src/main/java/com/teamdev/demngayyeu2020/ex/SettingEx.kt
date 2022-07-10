package com.teamdev.demngayyeu2020.ex

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.teamdev.demngayyeu2020.BuildConfig.VERSION_CODE
import com.teamdev.demngayyeu2020.BuildConfig.VERSION_NAME
import com.teamdev.demngayyeu2020.R
import com.teamdev.demngayyeu2020.app.ContextApplication

const val LINK_CH: String = "https://play.google.com/store/apps/details?id="

const val LINK_POLICY = "https://sites.google.com/view/couple-in-love/"

const val MAIL = "coupleinlove365@gmail.com"

fun Context.rate() {
    val appPackageName = packageName
    try {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(LINK_CH + appPackageName)
            ).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            })
    } catch (e: ActivityNotFoundException) {
        e.printStackTrace()
    }
}

fun Context.share() {
    val appPackageName = packageName
    val shareIntent = Intent(Intent.ACTION_SEND)
    shareIntent.type = "text/plain"
    val chooserIntent = Intent.createChooser(
        shareIntent,
        getStringCompat(R.string.txt_shareapp)
    )
    shareIntent.putExtra(
        Intent.EXTRA_TEXT, LINK_CH + appPackageName
    )
    val facebookBundle = Bundle()
    facebookBundle.putString(Intent.EXTRA_TEXT, LINK_CH + appPackageName)
    val replacement = Bundle()
    replacement.putBundle("com.facebook.katana", facebookBundle)
    chooserIntent.putExtra(Intent.EXTRA_REPLACEMENT_EXTRAS, replacement)
    chooserIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    startActivity(chooserIntent)
}

fun Context.policy() {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(LINK_POLICY)
    try {
        startActivity(intent)
    } catch (e: Exception) {

    }
}

@SuppressLint("IntentReset")
fun Context.feedback(body: String = "") {
    val mailIntent = Intent(Intent.ACTION_SEND)
    val versionCode: Int = VERSION_CODE
    val versionName: String = VERSION_NAME

    mailIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    mailIntent.type = "plain/text"
    mailIntent.setPackage("com.google.android.gm")

    val arrayOfNulls = arrayOfNulls<String>(1)
    arrayOfNulls[0] = MAIL
    mailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOfNulls)
    mailIntent.putExtra(Intent.EXTRA_SUBJECT, "${getString(R.string.txt_feedback_user)} ${getString(R.string.app_name)} versionCode(${versionCode}) - versionName(${versionName})")
    mailIntent.putExtra(Intent.EXTRA_TEXT, body)
    try {
        startActivity(mailIntent)
    } catch (e: Exception) {

    }
}

