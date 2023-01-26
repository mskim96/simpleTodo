package com.msk.simpletodo.presentation.util

import java.text.SimpleDateFormat
import java.util.*

fun convertTimestampToDate(time: Long?): String {
    val sdf = SimpleDateFormat("yyyy/MM/dd E", Locale("en", "ja"))
    if (time == null) {
        val today = System.currentTimeMillis()
        return sdf.format(today)
    } else {
        val today = time
        return sdf.format(today)
    }
}

fun convertTimestampToHour(): Int {
    val today = System.currentTimeMillis()
    val sdf = SimpleDateFormat("kk", Locale.JAPAN)
    return sdf.format(today).toInt()
}

fun String.dateTimeTrim(): String {
    return this.replace(" ", "")
}