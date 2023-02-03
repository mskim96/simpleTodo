package com.msk.simpletodo.domain.model

import java.time.LocalDate

data class TaskDate(private val selectedDate: LocalDate? = null) {
    var currentDate: LocalDate = selectedDate ?: LocalDate.now()
        private set
}
