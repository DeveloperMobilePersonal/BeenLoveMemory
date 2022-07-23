package com.teamdev.demngayyeu2020.ex

import android.content.Context
import com.teamdev.demngayyeu2020.R

fun getTitleTypePass(): String {
    val passType = Pref.getString(KEY_PASS_TYPE, KEY_PASS_DRAW)
    return if (passType == KEY_PASS_DRAW) {
        getStringCompat(R.string.txt_pass_draw)
    } else {
        getStringCompat(R.string.txt_pass_pin)
    }
}

fun getMessageTypePass(): String {
    val passType = Pref.getString(KEY_PASS_TYPE, KEY_PASS_DRAW)
    return if (passType == KEY_PASS_DRAW) {
        getStringCompat(R.string.txt_message_draw)
    } else {
        getStringCompat(R.string.txt_message_pin)
    }
}

fun getMessage2TypePass(): String {
    val passType = Pref.getString(KEY_PASS_TYPE, KEY_PASS_DRAW)
    return if (passType == KEY_PASS_DRAW) {
        getStringCompat(R.string.txt_pass_illegal)
    } else {
        ""
    }
}

fun getKeyTypePass(): String {
    return Pref.getString(KEY_PASS_TYPE, KEY_PASS_DRAW).toString()
}