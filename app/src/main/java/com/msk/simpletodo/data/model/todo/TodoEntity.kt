package com.msk.simpletodo.data.model.todo

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
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

    @ColumnInfo(name = "todo_updated")
    val updatedAt: Long = createdAt,

    @ColumnInfo(name = "todo_tag")
    val category: String,

    @ColumnInfo(name = "todo_date")
    val date: String,

    @ColumnInfo(name = "todo_time")
    val time: String,

    @ColumnInfo(name = "todo_done")
    val done: Boolean = false,

    @ColumnInfo(name = "todo_notification")
    val notification: Boolean = false,
) : Parcelable