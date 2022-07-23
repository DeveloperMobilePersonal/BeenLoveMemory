package com.teamdev.demngayyeu2020.ui.lock

import android.content.Context
import android.content.Intent
import com.lock.PinCallback
import com.teamdev.demngayyeu2020.R
import com.teamdev.demngayyeu2020.base.BaseActivity
import com.teamdev.demngayyeu2020.databinding.ActivitySetUpBinding
import com.teamdev.demngayyeu2020.dialog.menulock.DialogMenuLock
import com.teamdev.demngayyeu2020.ex.*
import com.teamdev.demngayyeu2020.ui.main.MainActivity
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class SetUpActivity : BaseActivity<ActivitySetUpBinding>(), PinCallback {

    companion object {
        fun open(context: Context) {
            val intent = Intent(context, SetUpActivity::class.java)
            context.startActivity(intent)
        }
    }

    private val pass: String
        get() = Pref.getString(KEY_LOCK_PASS, "").toString()
    private val dialogMenuLock by inject<DialogMenuLock> { parametersOf(true) }

    private var passCacheSetUp = ""

    override fun isStatusTransparent(): Boolean {
        return true
    }

    override fun loadUI(): Int {
        return R.layout.activity_set_up
    }

    override fun createUI() {
        viewBinding.ivBg.loadCacheAll(R.drawable.bg_default)
        if (pass.isNotEmpty()) {
            viewBinding.tvMessage.text = getString(R.string.txt_message_lock)
            viewBinding.llPassType.gone()
        }
        onLoadView()
        viewBinding.btResetPassWord.click {
            passCacheSetUp = ""
            onLoadView()
        }
        dialogMenuLock.listen = {
            onLoadView()
        }
        viewBinding.llPassType.click {
            dialogMenuLock.showUI()
        }
        viewBinding.lockView.addCallback(this)
        viewBinding.lockView.setType(getKeyTypePass() == KEY_PASS_PIN)
    }

    override fun destroyUI() {

    }

    private fun onLoadView() {
        passCacheSetUp = ""
        viewBinding.tvTypePass.text = getTitleTypePass()
        viewBinding.lockView.sendMessage(getMessageTypePass())
        viewBinding.lockView.sendMessage2(getMessage2TypePass())
        if (pass.isEmpty()) {
            viewBinding.llPassType.show()
            viewBinding.btResetPassWord.hide()
        } else {
            viewBinding.tvMessage.text = getStringCompat(R.string.app_name)
            viewBinding.llPassType.hide()
            viewBinding.btResetPassWord.hide()
        }
        viewBinding.lockView.setType(getKeyTypePass() == KEY_PASS_PIN)
    }

    override fun onPin(pin: String, type: String) {
        if (!isActive()) return
        if (pin.length < 4) {
            if (type == KEY_PASS_DRAW) {
                viewBinding.lockView.sendMessage2(getStringCompat(R.string.txt_pass_illegal), true)
            }
        } else {
            if (type == KEY_PASS_PIN) {
                if (pass.isNotEmpty()) {
                    if (pass == pin) {
                        MainActivity.open(this)
                    } else {
                        viewBinding.lockView.sendMessage2(
                            getStringCompat(R.string.txt_message_pin_incorrect),
                            true
                        )
                    }
                    return
                }
                if (passCacheSetUp.isEmpty()) {
                    viewBinding.lockView.sendMessage(getStringCompat(R.string.txt_message_pin_confirm))
                    viewBinding.lockView.sendMessage2("")
                } else {
                    if (passCacheSetUp == pin) {
                        Pref.postString(KEY_LOCK_PASS, pin)
                        showToast(R.string.txt_successful_lock_installation)
                        finish()
                    } else {
                        viewBinding.lockView.sendMessage2(getStringCompat(R.string.txt_message_pin_does_not_match))
                    }
                    return
                }
            } else if (type == KEY_PASS_DRAW) {
                if (pass.isNotEmpty()) {
                    if (pass == pin) {
                        MainActivity.open(this)
                    } else {
                        viewBinding.lockView.sendMessage2(
                            getStringCompat(R.string.txt_message_draw_incorrect),
                            true
                        )
                    }
                    return
                }
                if (passCacheSetUp.isEmpty()) {
                    viewBinding.lockView.sendMessage(getStringCompat(R.string.txt_message_draw_confirm))
                    viewBinding.lockView.sendMessage2(getStringCompat(R.string.txt_pass_illegal))
                } else {
                    if (passCacheSetUp == pin) {
                        Pref.postString(KEY_LOCK_PASS, pin)
                        showToast(R.string.txt_successful_lock_installation)
                        finish()
                    } else {
                        viewBinding.lockView.sendMessage2(
                            getStringCompat(R.string.txt_message_draw_does_not_match),
                            true
                        )
                    }
                    return
                }
            }
            viewBinding.btResetPassWord.show()
            viewBinding.llPassType.hide()
            passCacheSetUp = pin
        }
    }

    override fun onDrawChange() {
        viewBinding.lockView.sendMessage2(getStringCompat(R.string.txt_drop_arm))
    }
}