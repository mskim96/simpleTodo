package com.msk.simpletodo.data.model

import androidx.room.Embedded
import androidx.room.Relation


data class UserWithTodo(
    @Embedded val user: UserEntity,
    @Relation(
        parentColumn = "uid",
        entityColumn = "fk_utid"
    )
    val todoList: MutableList<TodoEntity> = mutableListOf()
)