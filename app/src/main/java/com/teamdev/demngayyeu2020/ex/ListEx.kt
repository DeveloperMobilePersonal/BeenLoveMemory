package com.teamdev.demngayyeu2020.ex

import com.teamdev.demngayyeu2020.R
import com.teamdev.demngayyeu2020.dialog.wave.SVGModel
import com.teamdev.demngayyeu2020.ui.bow.adapter.BowModel

val waveSVGList = getListWaveSvg()
val frameSVGList = getListFrame()

fun getWaveOrDefault(): SVGModel {
    val waveModelFake = SVGModel(Pref.idWaveSvg, -1, -1)
    return runCatching {
        val indexOf = waveSVGList.indexOf(waveModelFake)
        waveSVGList[indexOf]
    }.getOrElse { waveSVGList[0] }
}

fun getFrameOrDefault(id: Int): SVGModel {
    val frameModelFake = SVGModel(id, -1, -1)
    return runCatching {
        val indexOf = frameSVGList.indexOf(frameModelFake)
        frameSVGList[indexOf]
    }.getOrElse { frameSVGList[0] }
}

fun getListFrame(): MutableList<SVGModel> {
    return mutableListOf(
        SVGModel(0, R.drawable.ic_w_circle, R.raw.w_circle),
        SVGModel(2, R.drawable.ic_w_diamond, R.raw.w_diamond),
        SVGModel(3, R.drawable.ic_w_apple, R.raw.w_apple),
        SVGModel(4, R.drawable.ic_w_heart_1, R.raw.w_heart_1),
        SVGModel(5, R.drawable.ic_w_extension, R.raw.w_extension),
        SVGModel(6, R.drawable.ic_w_fire, R.raw.w_fire),
        SVGModel(7, R.drawable.ic_w_flower, R.raw.w_flower),
        SVGModel(8, R.drawable.ic_w_hexagon, R.raw.w_hexagon),
        SVGModel(9, R.drawable.ic_w_instagram, R.raw.w_instagram),
        SVGModel(10, R.drawable.ic_w_puzzle, R.raw.w_puzzle),
        SVGModel(11, R.drawable.ic_w_shit, R.raw.w_shit),
        SVGModel(12, R.drawable.ic_w_star, R.raw.w_star),
        SVGModel(13, R.drawable.ic_w_triangle, R.raw.w_triangle),
        SVGModel(14, R.drawable.ic_w_water, R.raw.w_water),
        SVGModel(15, R.drawable.ic_w_award, R.raw.w_award),
    )
}

fun getListWaveSvg(): MutableList<SVGModel> {
    return mutableListOf(
        SVGModel(0, R.drawable.ic_w_heart_1, R.raw.w_heart_1),
        SVGModel(1, R.drawable.ic_w_flower, R.raw.w_flower),
        SVGModel(2, R.drawable.ic_w_fire, R.raw.w_fire),
        SVGModel(3, R.drawable.wave_3, R.raw.wave_3),
        SVGModel(4, R.drawable.wave_4, R.raw.wave_4),
        SVGModel(5, R.drawable.ic_w_apple, R.raw.w_apple),
        SVGModel(6, R.drawable.wave_6, R.raw.wave_6),
        SVGModel(7, R.drawable.wave_7, R.raw.wave_7),
        SVGModel(8, R.drawable.wave_8, R.raw.wave_8),
        SVGModel(9, R.drawable.wave_9, R.raw.wave_9),
        SVGModel(10, R.drawable.wave_10, R.raw.wave_10),
        SVGModel(11, R.drawable.ic_w_circle, R.raw.w_circle),
        SVGModel(12, R.drawable.wave_26, R.raw.wave_26),
        SVGModel(13, R.drawable.wave_13, R.raw.wave_13),
        SVGModel(14, R.drawable.wave_14, R.raw.wave_14),
        SVGModel(15, R.drawable.wave_15, R.raw.wave_15),
        SVGModel(16, R.drawable.wave_16, R.raw.wave_16),
        SVGModel(17, R.drawable.wave_17, R.raw.wave_17),
        SVGModel(18, R.drawable.wave_18, R.raw.wave_18),
        SVGModel(19, R.drawable.wave_19, R.raw.wave_19),
        SVGModel(20, R.drawable.wave_20, R.raw.wave_20),
        SVGModel(21, R.drawable.wave_21, R.raw.wave_21),
        SVGModel(22, R.drawable.ic_w_shit, R.raw.w_shit),
        SVGModel(23, R.drawable.wave_27, R.raw.wave_27),
        SVGModel(24, R.drawable.wave_24, R.raw.wave_24),
        SVGModel(25, R.drawable.wave_25, R.raw.wave_25),
    )
}

fun getBowList(): MutableList<BowModel> {
    return mutableListOf(
        BowModel("${getStringCompat(R.string.bachduong)}\n(21/3-20/4)", R.drawable.ic_aries, "https://lichngaytot.com/cung-hoang-dao/bach-duong-1.html"),
        BowModel("${getStringCompat(R.string.kimnguu)}\n(21/4-20/5)", R.drawable.ic_taurus, "https://lichngaytot.com/cung-hoang-dao/kim-nguu-2.html"),
        BowModel("${getStringCompat(R.string.songtu)}\n(21/5-21/6)", R.drawable.ic_gemini, "https://lichngaytot.com/cung-hoang-dao/song-tu-3.html"),
        BowModel("${getStringCompat(R.string.cugiai)}\n(22/6-22/7)", R.drawable.ic_cancer, "https://lichngaytot.com/cung-hoang-dao/cu-giai-4.html"),
        BowModel("${getStringCompat(R.string.sutu)}\n(23/7-22/8)", R.drawable.ic_leo, "https://lichngaytot.com/cung-hoang-dao/su-tu-5.html"),
        BowModel("${getStringCompat(R.string.xunu)}\n(23/8-22/9)", R.drawable.ic_virgo, "https://lichngaytot.com/cung-hoang-dao/xu-nu-6.html"),
        BowModel("${getStringCompat(R.string.thienbinh)}\n(23/9-23/10)", R.drawable.ic_libra, "https://lichngaytot.com/cung-hoang-dao/thien-binh-7.html"),
        BowModel("${getStringCompat(R.string.bocap)}\n(24/10-22/11)", R.drawable.ic_scorpio, "https://lichngaytot.com/cung-hoang-dao/ho-cap-8.html"),
        BowModel("${getStringCompat(R.string.nhanma)}\n(23/11-21/12)", R.drawable.ic_sagittarius, "https://lichngaytot.com/cung-hoang-dao/nhan-ma-9.html"),
        BowModel("${getStringCompat(R.string.maket)}\n(22/12-19/1)", R.drawable.ic_capricornus, "https://lichngaytot.com/cung-hoang-dao/ma-ket-10.html"),
        BowModel("${getStringCompat(R.string.baobinh)}\n(20/1-18/2)", R.drawable.ic_aquarius, "https://lichngaytot.com/cung-hoang-dao/bao-binh-11.html"),
        BowModel("${getStringCompat(R.string.songngu)}\n(19/2-20/3)", R.drawable.ic_pisces, "https://lichngaytot.com/cung-hoang-dao/song-ngu-12.html"),
    )
}