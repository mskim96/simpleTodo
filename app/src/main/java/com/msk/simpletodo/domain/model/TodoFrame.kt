package com.msk.simpletodo.domain.model

sealed interface TodoType {
    object Person : TodoType
    object Work : TodoType
    object Study : TodoType
}

data class TodoFrame(
    val type: TodoType,
    val title: String,
)