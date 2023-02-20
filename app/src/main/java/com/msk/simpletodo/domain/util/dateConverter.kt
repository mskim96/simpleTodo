package com.msk.simpletodo.domain.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun LocalDateTime.formatQuery(query: String): String {
    val sdf = DateTimeFormatter.ofPattern(query)
    return sdf.format(this)
}