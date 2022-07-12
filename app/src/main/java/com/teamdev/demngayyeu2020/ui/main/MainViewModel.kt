package com.teamdev.demngayyeu2020.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamdev.demngayyeu2020.R
import com.teamdev.demngayyeu2020.app.ContextApplication
import com.teamdev.demngayyeu2020.ex.*
import com.teamdev.demngayyeu2020.service.NotificationService
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val liveBackground by lazy { MutableLiveData<Any>(Pref.background.ifEmpty { R.drawable.bg_default }) }
    val liveLoveDay by lazy { MutableLiveData(getLoveDay(Pref.loveDay)) }

    val liveNameMale by lazy { MutableLiveData(Pref.nameMale) }
    val liveNameFemale by lazy { MutableLiveData(Pref.nameFeMale) }

    val liveLetterNameLocation by lazy { MutableLiveData(Pref.letterNameLocation) }
    val liveLetterMessageCustom by lazy { MutableLiveData(Pref.letterMessageCustom) }
    val liveHasLetterMessageCustom by lazy { MutableLiveData(Pref.letterMessageCustom.isNotEmpty()) }

    val liveAgeMale by lazy { MutableLiveData("0") }
    val liveAgeFeMale by lazy { MutableLiveData("0") }

    val liveBowMale by lazy { MutableLiveData(getStringCompat(R.string.txt_bow)) }
    val liveBowIconMale by lazy { MutableLiveData(R.drawable.ic_aquarius) }
    val liveBowFeMale by lazy { MutableLiveData(getStringCompat(R.string.txt_bow)) }
    val liveBowIconFeMale by lazy { MutableLiveData(R.drawable.ic_aquarius) }

    val liveFaceMale by lazy { MutableLiveData<Any>(Pref.faceMale.ifEmpty { R.drawable.ic_male_default }) }
    val liveFaceFeMale by lazy { MutableLiveData<Any>(Pref.faceFeMale.ifEmpty { R.drawable.ic_female_default }) }

    val liveStatusIcon by lazy { MutableLiveData<Any>(R.drawable.ic_favourite_2) }

    val liveFrameImageMale by lazy { MutableLiveData(getFrameOrDefault(Pref.idFrameMaleSvg).rawSvg) }
    val liveFrameImageFeMale by lazy { MutableLiveData(getFrameOrDefault(Pref.idFrameFeMaleSvg).rawSvg) }

    val liveStatusTop by lazy { MutableLiveData(Pref.txtTop) }
    val liveStatusBottom by lazy { MutableLiveData(Pref.txtBottom) }

    val liveSexLocation by lazy { MutableLiveData(Pref.sexLocation) }

    val liveBgAgeMale by lazy { MutableLiveData(createShape(100f, Pref.ageMaleBgColor)) }
    val liveBgAgeFeMale by lazy { MutableLiveData(createShape(100f, Pref.ageFeMaleBgColor)) }
    val liveBgBowMale by lazy { MutableLiveData(createShape(100f, Pref.bowMaleBgColor)) }
    val liveBgBowFeMale by lazy { MutableLiveData(createShape(100f, Pref.bowFeMaleBgColor)) }

    fun postBirthdayMale() {
        if (Pref.birthdayMale == "0") return
        viewModelScope.launch {
            val infoWithDate = getInfoWithDate(Pref.birthdayMale)
            liveAgeMale.postValue(infoWithDate.first)
            liveBowMale.postValue(infoWithDate.second)
            liveBowIconMale.postValue(infoWithDate.third)
        }
    }

    fun postBirthdayFeMale() {
        if (Pref.birthdayFeMale == "0") return
        viewModelScope.launch {
            val infoWithDate = getInfoWithDate(Pref.birthdayFeMale)
            liveAgeFeMale.postValue(infoWithDate.first)
            liveBowFeMale.postValue(infoWithDate.second)
            liveBowIconFeMale.postValue(infoWithDate.third)
        }
    }

    fun postBgAgeMale() {
        viewModelScope.launch {
            liveBgAgeMale.value = createShape(100f, Pref.ageMaleBgColor)
        }
    }

    fun postBgAgeFeMale() {
        viewModelScope.launch {
            liveBgAgeFeMale.value = createShape(100f, Pref.ageFeMaleBgColor)
        }
    }

    fun postBgBowMale() {
        viewModelScope.launch {
            liveBgBowMale.value = createShape(100f, Pref.bowMaleBgColor)
        }
    }

    fun postBgBowFeMale() {
        viewModelScope.launch {
            liveBgBowFeMale.value = createShape(100f, Pref.bowFeMaleBgColor)
        }
    }

    fun postLoveDay() {
        viewModelScope.launch {
            liveLoveDay.value = getLoveDay(Pref.loveDay)
        }
        NotificationService.startOrStop(ContextApplication.get(), false)
    }

}