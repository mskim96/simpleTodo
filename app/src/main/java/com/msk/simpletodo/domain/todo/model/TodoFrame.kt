package com.msk.simpletodo.domain.todo.model

sealed interface TodoType {
    object Person : TodoType
    object Work : TodoType
    object Study : TodoType
}

data class TodoFrame(
    val type: TodoType,
    val title: String,
)