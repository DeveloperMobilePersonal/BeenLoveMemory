package com.teamdev.demngayyeu2020.ex

import android.content.Context
import android.graphics.Color
import com.getkeepsafe.relinker.ReLinker
import com.teamdev.demngayyeu2020.R
import com.tencent.mmkv.MMKV

const val COLOR_DEFAULT = "#EC2994"
const val ID_SVG_DEFAULT = 0

const val KEY_WAVE_COLOR = "wave_color"
const val KEY_WAVE_SVG = "wave_svg"

const val KEY_NAME_MALE = "name_male"
const val KEY_FACE_MALE = "face_male"
const val KEY_BIRTHDAY_MALE = "birthday_male"
const val KEY_NAME_FEMALE = "name_female"
const val KEY_FACE_FEMALE = "face_female"
const val KEY_BIRTHDAY_FEMALE = "birthday_female"
const val KEY_LOVE_DAY = "love_day"
const val KEY_BACKGROUND = "background"
const val KEY_IS_CALCULATE_ONE = "is_calculate_one"
const val KEY_STATUS_ICON = "status_icon"
const val KEY_TXT_TOP = "txt_top"
const val KEY_TXT_BOTTOM = "txt_bottom"


const val KEY_FRAME_FACE_MALE = "frame_face_male"
const val KEY_FRAME_FACE_FEMALE = "frame_face_female"

const val KEY_AGE_MALE_BG_COLOR = "age_male_bg_color"
const val KEY_AGE_FEMALE_BG_COLOR = "age_female_bg_color"
const val KEY_BOW_MALE_BG_COLOR = "bow_male_bg_color"
const val KEY_BOW_FEMALE_BG_COLOR = "bow_female_bg_color"

const val KEY_AGE_MALE = "age_male"
const val KEY_AGE_FEMALE = "age_female"
const val KEY_BOW_MALE = "bow_male"
const val KEY_BOW_FEMALE = "bow_female"
const val KEY_LETTER = "letter"
const val KEY_DIARY = "diary"

const val KEY_SEX_LOCATION = "sex_location"
const val KEY_LETTER_NAME_LOCATION = "letter_name_location"
const val KEY_LETTER_MESSAGE_CUSTOM = "letter_message_custom"
const val KEY_NOTIFICATION = "notification"


object Pref {

    private lateinit var mmkv: MMKV

    @Synchronized
    fun load(context: Context) {
        if (!::mmkv.isInitialized) {
            MMKV.initialize(context) { libName: String? ->
                ReLinker.loadLibrary(context, libName)
            }
            mmkv = MMKV.defaultMMKV(MMKV.MULTI_PROCESS_MODE, null)
        }
    }

    fun postBool(key: String, state: Boolean = false) {
        mmkv.encode(key, state)
    }

    fun getBool(key: String, default: Boolean = false): Boolean {
        return mmkv.decodeBool(key, default)
    }

    fun postString(key: String, value: String = "") {
        mmkv.encode(key, value)
    }

    fun getString(key: String, default: String? = ""): String? {
        return mmkv.decodeString(key, default)
    }

    fun getInt(key: String, default: Int = 0): Int {
        return mmkv.decodeInt(key, default)
    }

    fun postInt(key: String, value: Int = 0) {
        mmkv.encode(key, value)
    }

    fun postLong(key: String, value: Long) {
        mmkv.encode(key, value)
    }

    fun clearKey(key: String? = "") {
        mmkv.removeValueForKey(key)
    }

    var waveColor: Int
        get() = getInt(KEY_WAVE_COLOR, -1).takeIf { it != -1 } ?: getColorCompat(R.color.pinkEC2994)
        set(value) {
            postInt(KEY_WAVE_COLOR, value)
        }

    var background: String
        get() = getString(KEY_BACKGROUND, "").toString()
        set(value) {
            postString(KEY_BACKGROUND, value)
        }

    var loveDay: String
        get() = getString(KEY_LOVE_DAY, "").toString()
        set(value) {
            postString(KEY_LOVE_DAY, value)
        }

    var nameMale: String
        get() = getString(KEY_NAME_MALE, getStringCompat(R.string.txt_nick_name_1)).toString()
        set(value) {
            postString(KEY_NAME_MALE, value)
        }

    var nameFeMale: String
        get() = getString(KEY_NAME_FEMALE, getStringCompat(R.string.txt_nick_name_2)).toString()
        set(value) {
            postString(KEY_NAME_FEMALE, value)
        }

    var faceMale: String
        get() = getString(KEY_FACE_MALE, "").toString()
        set(value) {
            postString(KEY_FACE_MALE, value)
        }

    var faceFeMale: String
        get() = getString(KEY_FACE_FEMALE, "").toString()
        set(value) {
            postString(KEY_FACE_FEMALE, value)
        }

    var birthdayMale: String
        get() = getString(KEY_BIRTHDAY_MALE, "0").toString()
        set(value) {
            postString(KEY_BIRTHDAY_MALE, value)
        }

    var birthdayFeMale: String
        get() = getString(KEY_BIRTHDAY_FEMALE, "0").toString()
        set(value) {
            postString(KEY_BIRTHDAY_FEMALE, value)
        }

    var txtTop: String
        get() = getString(KEY_TXT_TOP, getStringCompat(R.string.txt_dating)).toString()
        set(value) {
            postString(KEY_TXT_TOP, value)
        }

    var txtBottom: String
        get() = getString(KEY_TXT_BOTTOM, getStringCompat(R.string.txt_day)).toString()
        set(value) {
            postString(KEY_TXT_BOTTOM, value)
        }

    var ageMaleBgColor: Int
        get() = getInt(KEY_AGE_MALE_BG_COLOR, Color.parseColor(COLOR_DEFAULT))
        set(value) {
            postInt(KEY_AGE_MALE_BG_COLOR, value)
        }

    var ageFeMaleBgColor: Int
        get() = getInt(KEY_AGE_FEMALE_BG_COLOR, Color.parseColor(COLOR_DEFAULT))
        set(value) {
            postInt(KEY_AGE_FEMALE_BG_COLOR, value)
        }

    var bowMaleBgColor: Int
        get() = getInt(KEY_BOW_MALE_BG_COLOR, Color.parseColor(COLOR_DEFAULT))
        set(value) {
            postInt(KEY_BOW_MALE_BG_COLOR, value)
        }

    var bowFeMaleBgColor: Int
        get() = getInt(KEY_BOW_FEMALE_BG_COLOR, Color.parseColor(COLOR_DEFAULT))
        set(value) {
            postInt(KEY_BOW_FEMALE_BG_COLOR, value)
        }

    var sexLocation: Boolean
        get() = getBool(KEY_SEX_LOCATION, true)
        set(value) {
            postBool(KEY_SEX_LOCATION, value)
        }

    var letterNameLocation: Boolean
        get() = getBool(KEY_LETTER_NAME_LOCATION, true)
        set(value) {
            postBool(KEY_LETTER_NAME_LOCATION, value)
        }

    var isCalculateOne: Boolean
        get() = getBool(KEY_IS_CALCULATE_ONE, true)
        set(value) {
            postBool(KEY_IS_CALCULATE_ONE, value)
        }

    var idWaveSvg: Int
        get() = getInt(KEY_WAVE_SVG, ID_SVG_DEFAULT)
        set(value) {
            postInt(KEY_WAVE_SVG, value)
        }

    var idFrameMaleSvg: Int
        get() = getInt(KEY_FRAME_FACE_MALE, ID_SVG_DEFAULT)
        set(value) {
            postInt(KEY_FRAME_FACE_MALE, value)
        }

    var idFrameFeMaleSvg: Int
        get() = getInt(KEY_FRAME_FACE_FEMALE, ID_SVG_DEFAULT)
        set(value) {
            postInt(KEY_FRAME_FACE_FEMALE, value)
        }

    var letterMessageCustom: String
        get() = getString(KEY_LETTER_MESSAGE_CUSTOM, "").toString()
        set(value) {
            postString(KEY_LETTER_MESSAGE_CUSTOM, value)
        }

    var isNotification: Boolean
        get() = getBool(KEY_NOTIFICATION, false)
        set(value) {
            postBool(KEY_NOTIFICATION, value)
        }
}