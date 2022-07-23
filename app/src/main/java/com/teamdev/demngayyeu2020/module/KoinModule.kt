package com.teamdev.demngayyeu2020.module

import com.teamdev.demngayyeu2020.dialog.capture.DialogCapture
import com.teamdev.demngayyeu2020.dialog.color.DialogColor
import com.teamdev.demngayyeu2020.dialog.date.DialogDate
import com.teamdev.demngayyeu2020.dialog.input.DialogInput
import com.teamdev.demngayyeu2020.dialog.menu.birthday.DialogMenuBirthday
import com.teamdev.demngayyeu2020.dialog.menu.diary.DialogMenuDiary
import com.teamdev.demngayyeu2020.dialog.menu.info.DialogMenuInfo
import com.teamdev.demngayyeu2020.dialog.menu.letter.DialogMenuLetter
import com.teamdev.demngayyeu2020.dialog.menu.wave.DialogMenuWithWave
import com.teamdev.demngayyeu2020.dialog.menulock.DialogMenuLock
import com.teamdev.demngayyeu2020.dialog.rate.DialogRate
import com.teamdev.demngayyeu2020.dialog.wave.DialogMenuSVG
import com.teamdev.demngayyeu2020.dialog.wave.adapter.SVGAdapter
import com.teamdev.demngayyeu2020.permission.PermissionManager
import com.teamdev.demngayyeu2020.room.RoomManager
import com.teamdev.demngayyeu2020.scan.ScanPhoto
import com.teamdev.demngayyeu2020.service.NotificationHelper
import com.teamdev.demngayyeu2020.service.NotificationService
import com.teamdev.demngayyeu2020.ui.main.MainActivity
import com.teamdev.demngayyeu2020.ui.main.MainViewModel
import com.teamdev.demngayyeu2020.ui.adapter.ViewPagerAdapter
import com.teamdev.demngayyeu2020.ui.bow.BowActivity
import com.teamdev.demngayyeu2020.ui.bow.adapter.BowAdapter
import com.teamdev.demngayyeu2020.ui.crop.GalleryActivity
import com.teamdev.demngayyeu2020.ui.crop.adapter.GalleryAdapter
import com.teamdev.demngayyeu2020.ui.diary.DiaryActivity
import com.teamdev.demngayyeu2020.ui.fragment.diary.FragmentDiary
import com.teamdev.demngayyeu2020.ui.fragment.diary.adapter.DiaryAdapter
import com.teamdev.demngayyeu2020.ui.fragment.main.FragmentMain
import com.teamdev.demngayyeu2020.ui.fragment.main.fragment.FragmentDate
import com.teamdev.demngayyeu2020.ui.fragment.main.fragment.FragmentWave
import com.teamdev.demngayyeu2020.ui.fragment.setting.FragmentSetting
import com.teamdev.demngayyeu2020.ui.lock.SetUpActivity
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataModule = module {
    single { RoomManager(get()) }
}

val serviceModule = module {
    scope<NotificationService> {
        factory { NotificationHelper(get()) }
    }
}
val activityModule = module {
    scope<MainActivity> {
        viewModel { MainViewModel() }
        fragment { FragmentMain() }
        fragment { FragmentDiary() }
        fragment { FragmentSetting() }
        fragment { FragmentWave() }
        fragment { FragmentDate() }
        factory { DiaryAdapter(get()) }
        factory { SVGAdapter(get()) }
        factory { DialogMenuWithWave(get()) }
        factory { DialogInput(get()) }
        factory { DialogDate(get()) }
        factory { DialogColor(get()) }
        factory { DialogMenuBirthday(get()) }
        factory { DialogMenuInfo(get()) }
        factory { DialogMenuSVG(get(), get()) }
        factory { DialogMenuLetter(get()) }
        factory { DialogMenuDiary(get()) }
        factory { DialogCapture(get()) }
        factory { DialogRate(get()) }
    }
    scope<GalleryActivity> {
        factory { GalleryAdapter(get()) }
        factory { ScanPhoto(get()) }
        factory { PermissionManager(get()) }
    }
    scope<DiaryActivity> {
        factory { DialogDate(get()) }
    }
    scope<SetUpActivity> {
        factory { DialogMenuLock(get(), get()) }
    }
    scope<BowActivity> {
        factory { BowAdapter(get()) }
    }
}

val factoryModule = module {
    factory { ViewPagerAdapter(get(), get()) }
}

val listModule = listOf(dataModule, activityModule, factoryModule, serviceModule)
