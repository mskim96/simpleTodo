package com.msk.simpletodo.domain.todo.model

data class ToDo(
    val type: TodoType,
    val title: String?,
    val date: String?,
    val overToDo: Boolean? = null
)