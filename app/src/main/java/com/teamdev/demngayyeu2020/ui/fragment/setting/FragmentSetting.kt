package com.teamdev.demngayyeu2020.ui.fragment.setting

import com.teamdev.demngayyeu2020.R
import com.teamdev.demngayyeu2020.base.BaseFragment
import com.teamdev.demngayyeu2020.databinding.FragmentSettingBinding
import com.teamdev.demngayyeu2020.ex.*
import com.teamdev.demngayyeu2020.service.NotificationService
import com.teamdev.demngayyeu2020.ui.bow.BowActivity
import com.teamdev.demngayyeu2020.ui.lock.SetUpActivity
import com.teamdev.demngayyeu2020.ui.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FragmentSetting : BaseFragment<FragmentSettingBinding>() {

    private val mainViewModel: MainViewModel by sharedViewModel()

    private val iconCalculateOne: Int
        get() = if (Pref.isCalculateOne) {
            R.drawable.ic_on_button
        } else {
            R.drawable.ic_off_button
        }

    private val iconNotification: Int
        get() = if (Pref.isNotification) {
            R.drawable.ic_on_button
        } else {
            R.drawable.ic_off_button
        }

    private val iconLock: Int
        get() = if (Pref.getString(KEY_LOCK_PASS, "").toString().isNotEmpty()) {
            R.drawable.ic_on_button
        } else {
            R.drawable.ic_off_button
        }

    override fun loadUI(): Int {
        return R.layout.fragment_setting
    }

    override fun createUI() {
        runMainActivity { activity ->
            NotificationService.startOrStop(activity)
            viewBinding.ivSwitchCalculateOne.setImageResource(iconCalculateOne)
            viewBinding.ivSwitchNotification.setImageResource(iconNotification)
            viewBinding.llCalculateOne.click {
                Pref.isCalculateOne = !Pref.isCalculateOne
                mainViewModel.liveLoveDay.value = getLoveDay(Pref.loveDay)
                viewBinding.ivSwitchCalculateOne.setImageResource(iconCalculateOne)
                NotificationService.startOrStop(activity)
            }
            viewBinding.llNotification.click {
                Pref.isNotification = !Pref.isNotification
                viewBinding.ivSwitchNotification.setImageResource(iconNotification)
                NotificationService.startOrStop(activity)
            }
            viewBinding.llLock.click {
                if (Pref.getString(KEY_LOCK_PASS, "").toString().isNotEmpty()) {
                    Pref.postString(KEY_LOCK_PASS, "")
                    viewBinding.ivSwitchLock.setImageResource(iconLock)
                    showToast(R.string.txt_lock_off)
                } else {
                    SetUpActivity.open(activity)
                }
            }
            viewBinding.llBow.click {
               BowActivity.open(activity)
            }
            viewBinding.llRate.click {
                activity.rate()
            }
            viewBinding.llFeedback.click {
                activity.feedback()
            }
            viewBinding.llPolicy.click {
                activity.policy()
            }
        }
    }

    override fun onResume() {
        runMainActivity {
            viewBinding.ivSwitchLock.setImageResource(iconLock)
        }
        super.onResume()
    }

    override fun destroyUI() {

    }
}