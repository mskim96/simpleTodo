package com.msk.simpletodo.data.model.todo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_category_table")
data class TodoCategory(
    @PrimaryKey(autoGenerate = true)
    val tid: Long = 0,

    @ColumnInfo(name = "todo_category")
    val category: String,

    @ColumnInfo(name = "todo_category_icon")
    val categoryIcon: String,

    )