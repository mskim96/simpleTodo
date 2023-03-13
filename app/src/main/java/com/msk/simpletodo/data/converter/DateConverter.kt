package com.msk.simpletodo.data.converter

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

const val DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss"

class DateConverter {
    private val formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)

    @TypeConverter
    fun fromString(value: String): LocalDateTime {
        return LocalDateTime.parse(value, formatter)
    }

    @TypeConverter
    fun fromTimeStamp(dateTime: LocalDateTime): String {
        return formatter.format(dateTime)
    }
}