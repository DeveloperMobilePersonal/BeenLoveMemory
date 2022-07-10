package com.teamdev.demngayyeu2020.scan

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore

class ScanPhoto(private val context: Context) {

    private var isDestroy = false

    @SuppressLint("Recycle", "Range")
    fun startScan(): MutableList<PhotoModel> {
        val uri: Uri = MediaStore.Files.getContentUri("external")
        val cr: ContentResolver = context.contentResolver
        val arrayOf = arrayOf(MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA)
        val cursor: Cursor? = cr.query(
            uri,
            arrayOf,
            null,
            null,
            MediaStore.Files.FileColumns.DATE_MODIFIED + " DESC"
        )
        val list = mutableListOf<PhotoModel>()
        if (cursor != null && cursor.count > 0 && !isDestroy) {
            while (cursor.moveToNext()) {
                val id = kotlin.runCatching { cursor.getLong(cursor.getColumnIndex(arrayOf[0])) }
                    .getOrElse { -1 }
                val path =
                    kotlin.runCatching { cursor.getString(cursor.getColumnIndex(arrayOf[1])) }
                        .getOrElse { "" }
                if (id != -1L) {
                    list.add(PhotoModel(id, path))
                }
            }
        }
        cursor?.close()
        return list
    }

    fun stopScan() {
        isDestroy = true
    }
}