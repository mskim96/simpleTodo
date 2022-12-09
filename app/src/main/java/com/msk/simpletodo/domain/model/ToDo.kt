package com.msk.simpletodo.domain.model

data class ToDo(
    val type: TodoType,
    val title: String?,
    val date: String?,
    val overToDo: Boolean? = null
)