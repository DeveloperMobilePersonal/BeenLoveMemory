package com.teamdev.demngayyeu2020.room

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import com.teamdev.demngayyeu2020.room.diary.DiaryModel

@Dao
interface Dao {
    @Insert
    suspend fun insert(user: DiaryModel)

    @Update
    suspend fun update(user: DiaryModel)

    @Delete
    suspend fun delete(user: DiaryModel)

    @Query("SELECT * FROM diary ODER ORDER BY id DESC")
    fun getDiaryLive(): LiveData<MutableList<DiaryModel>?>

    @Query("SELECT * FROM diary ODER WHERE id =:id")
    fun getDiary(id: Long): DiaryModel?

}