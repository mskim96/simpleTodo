package com.msk.simpletodo.presentation.util

import java.text.SimpleDateFormat
import java.util.*

fun convertTimestampToDate(): String {
    val today = System.currentTimeMillis()
    val sdf = SimpleDateFormat("yyyy/MM/dd", Locale.JAPAN)
    return sdf.format(today)
}

fun convertTimestampToHour(): Int {
    val today = System.currentTimeMillis()
    val sdf = SimpleDateFormat("kk", Locale.JAPAN)
    return sdf.format(today).toInt()
}