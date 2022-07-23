package com.lock

interface PinCallback {
    fun onPin(pin: String, type:String)
    fun onDrawChange()
}