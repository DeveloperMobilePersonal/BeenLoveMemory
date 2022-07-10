package com.teamdev.demngayyeu2020.room

import android.content.Context
import androidx.lifecycle.LiveData
import com.teamdev.demngayyeu2020.room.diary.DiaryModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RoomManager(context: Context) {

    private val daoUser = loadRoomHelper(context).userDao()
    private val scope = CoroutineScope(Dispatchers.IO)

    fun insertDiary(diaryModel: DiaryModel) {
        scope.launch { daoUser.insert(diaryModel) }
    }

    fun updateDiary(diaryModel: DiaryModel) {
        scope.launch { daoUser.update(diaryModel) }
    }

    fun deleteDiary(diaryModel: DiaryModel) {
        scope.launch { daoUser.delete(diaryModel) }
    }

    fun getDiary(id:Long): DiaryModel? {
        return daoUser.getDiary(id)
    }

    fun getLiveListDiary(): LiveData<MutableList<DiaryModel>?> {
        return daoUser.getDiaryLive()
    }

}