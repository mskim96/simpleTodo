package com.msk.simpletodo.data.model.todo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_table")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    val tid: Long = 0,

    @ColumnInfo(name = "todo_title")
    val content: String,

    @ColumnInfo(name = "todo_createdAt")
    val createdAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "todo_updatedAt")
    val updatedAt: Long = createdAt,

    @ColumnInfo(name = "todo_done")
    val done: Boolean = false,

    @ColumnInfo(name = "fk_category", index = true)
    val fkCategory: Long
)