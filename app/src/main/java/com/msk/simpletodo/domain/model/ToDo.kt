package com.msk.simpletodo.domain.model

data class ToDo(
    val title: String?,
    val date: String?,
    val overToDo: Boolean? = null
)