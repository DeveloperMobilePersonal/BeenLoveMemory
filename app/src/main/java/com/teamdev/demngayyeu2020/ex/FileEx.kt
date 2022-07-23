package com.teamdev.demngayyeu2020.ex

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

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

fun Context.getPathCapture(): String {
    val externalFilesDir = getExternalFilesDir("screenshot") ?: return ""
    if (!externalFilesDir.exists()) {
        externalFilesDir.mkdirs()
    }
    return externalFilesDir.absolutePath.toString() + "/screenshot_${System.currentTimeMillis()}.png"
}

fun Context.saveBitmap(
    bitmap: Bitmap,
    result: ((String) -> Unit)? = null
) {
    if (bitmap.isRecycled) {
        result?.let { it("") }
        return
    }
    val bytes = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
    val file = File(getPathCapture())
    try {
        val fileOutputStream = FileOutputStream(file)
        fileOutputStream.write(bytes.toByteArray())
        fileOutputStream.close()
        bitmap.recycle()
        result?.let { it(file.path) }
    } catch (e: Exception) {
        result?.let { it("") }
    }
}

fun File.getUriFromFile(context: Context): Uri? =
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
        Uri.fromFile(this)
    } else {
        try {
            FileProvider.getUriForFile(context, context.packageName + ".provider", this)
        } catch (e: Exception) {
            throw if (e.message?.contains("ProviderInfo.loadXmlMetaData") == true) {
                Error("FileProvider is not set or doesn't have needed permissions")
            } else {
                e
            }
        }
    }