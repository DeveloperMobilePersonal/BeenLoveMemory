package com.teamdev.demngayyeu2020.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.teamdev.demngayyeu2020.ex.showLog
import com.teamdev.demngayyeu2020.room.diary.DiaryModel

@Database(entities = [DiaryModel::class], version = 2)
abstract class RoomUserHelper : RoomDatabase() {
    abstract fun userDao(): Dao
}

fun loadRoomHelper(context: Context): RoomUserHelper {
    return Room.databaseBuilder(context, RoomUserHelper::class.java, "love.db")
        .fallbackToDestructiveMigration()
        .addCallback(object : RoomDatabase.Callback() {
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                showLog(javaClass, db.path)
            }
        })
        .build()
}