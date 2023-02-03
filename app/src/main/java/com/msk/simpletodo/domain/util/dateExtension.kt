package com.msk.simpletodo.domain.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters

fun LocalDate.getFullDateByString(): String {
    val sdf = DateTimeFormatter.ofPattern("yyyy/MM/dd")
    return sdf.format(this)
}

fun LocalDate.getLastDayOfMonth(): String {
    return this.with(TemporalAdjusters.lastDayOfMonth()).format(DateTimeFormatter.ofPattern("dd"))
}

fun LocalDate.getLastDayOfMonthInt(): Int {
    return this.getLastDayOfMonth().toInt()
}