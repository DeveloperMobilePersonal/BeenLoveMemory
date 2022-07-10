package com.teamdev.demngayyeu2020.room.diary

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.teamdev.demngayyeu2020.ex.getDiaryDate

@Entity(tableName = "diary")
data class DiaryModel(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var date: String,
    var content: String,
    var path: String
) {
    @Ignore
    fun getDateFormat(): String {
        return getDiaryDate(date)
    }

    @Ignore
    override fun equals(other: Any?): Boolean {
        return (other is DiaryModel) && other.id == id
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + date.hashCode()
        result = 31 * result + content.hashCode()
        result = 31 * result + path.hashCode()
        return result
    }
}