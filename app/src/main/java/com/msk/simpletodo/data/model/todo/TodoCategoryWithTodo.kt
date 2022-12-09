package com.msk.simpletodo.data.model.todo

import androidx.room.Embedded
import androidx.room.Relation

data class TodoCategoryWithTodo(
    @Embedded
    val todoCategory: TodoCategory,

    @Relation(
        parentColumn = "tid",
        entityColumn = "fk_category"
    )

    val todo: List<TodoEntity> = mutableListOf()
)