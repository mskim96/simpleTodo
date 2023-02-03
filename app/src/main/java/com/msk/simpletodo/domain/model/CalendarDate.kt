package com.msk.simpletodo.domain.model

import com.msk.simpletodo.domain.util.getLastDayOfMonthInt
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*

data class CalendarDate(
    val date: String,
    val day: String,
    var isSelected: Boolean = false,
) {
    companion object {
        fun setDate(taskDate: TaskDate): ArrayList<CalendarDate> {
            val dateList = arrayListOf<CalendarDate>()

            val currentDate = taskDate.currentDate
            val lastDayOfMonth = currentDate.getLastDayOfMonthInt()
            for (i: Int in 1..lastDayOfMonth) {
                val date = LocalDate.of(currentDate.year, currentDate.month, i)
                val dayOfWeek = date.dayOfWeek
                dayOfWeek.getDisplayName(TextStyle.SHORT, Locale("en", "ja"))
                dateList.add(
                    CalendarDate(i.toString(), dayOfWeek.toString().substring(0, 3))
                )
            }
            return dateList
        }
    }
}