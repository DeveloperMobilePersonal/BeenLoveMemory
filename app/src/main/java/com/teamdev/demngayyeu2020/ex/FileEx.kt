package com.teamdev.demngayyeu2020.ex

import android.content.Context

fun Context.getPathFace(key: String): String {
    return getExternalFace(key) + "/thumbnail_$key${System.currentTimeMillis()}"
}

fun Context.getPathDiary(key: String): String {
    val externalFilesDir = getExternalFilesDir(key) ?: return ""
    if (!externalFilesDir.exists()) {
        externalFilesDir.mkdirs()
    }
    return externalFilesDir.absolutePath.toString() + "/${key}_${System.currentTimeMillis()}"
}

private fun Context.getExternalFace(key: String): String {
    val externalFilesDir = getExternalFilesDir(key) ?: return ""
    externalFilesDir.deleteRecursively()
    if (!externalFilesDir.exists()) {
        externalFilesDir.mkdirs()
    }
    return externalFilesDir.absolutePath.toString()
}