package com.msk.simpletodo.data.model.todo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_table")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    val tid: Long = 0,

    @ColumnInfo(name = "todo_title")
    val title: String,

    @ColumnInfo(name = "todo_description")
    val description: String,

    @ColumnInfo(name = "todo_created")
    val createdAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "todo_tag")
    val category: String,

    @ColumnInfo(name = "todo_date")
    val date: String,

    @ColumnInfo(name = "todo_time")
    val time: String,

    @ColumnInfo(name = "todo_done")
    val done: Boolean = false,
)