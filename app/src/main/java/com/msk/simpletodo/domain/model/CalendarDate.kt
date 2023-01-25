package com.msk.simpletodo.domain.model

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.time.temporal.TemporalAdjusters
import java.util.*

data class CalendarDate(
    val date: String,
    val day: String,
    var isSelected: Boolean = false
) {
    companion object {
        fun setDate(): ArrayList<CalendarDate> {
            val dateList = arrayListOf<CalendarDate>()

            val lastDayOfMonth = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth())
                .format(DateTimeFormatter.ofPattern("dd"))
            for (i: Int in 1..lastDayOfMonth.toInt()) {
                val date = LocalDate.of(LocalDate.now().year, LocalDate.now().month, i)
                val dayOfWeek: DayOfWeek = date.dayOfWeek
                dayOfWeek.getDisplayName(TextStyle.SHORT, Locale("en", "ja"))
                dateList.add(
                    CalendarDate(i.toString(), dayOfWeek.toString().substring(0, 3))
                )
            }
            return dateList
        }
    }
}