package com.msk.simpletodo.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.msk.simpletodo.domain.todo.model.TodoType


@Entity(
    tableName = "todo_list",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["uid"],
            childColumns = ["fk_utid"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    val tid: Long = 0,

    @ColumnInfo(name = "todo_type")
    val todoType: String,

    @ColumnInfo(name = "todo_title")
    val title: String,

    @ColumnInfo(name = "todo_createdAt")
    val createdAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "todo_updatedAt")
    val updatedAt: Long = createdAt,

    @ColumnInfo(name = "todo_done")
    val done: Boolean = false,

    @ColumnInfo(name = "fk_utid", index = true)
    val utid: Long = 0
)