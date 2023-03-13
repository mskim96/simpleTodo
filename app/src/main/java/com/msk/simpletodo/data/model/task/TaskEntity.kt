package com.msk.simpletodo.data.model.task

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "tasks")
data class TaskEntity @JvmOverloads constructor(
    @PrimaryKey(autoGenerate = true) var tid: Long = 0,
    @ColumnInfo(name = "title") var title: String = "",
    @ColumnInfo(name = "description") var description: String = "",
    @ColumnInfo(name = "category") var category: String,
    @ColumnInfo(name = "date") var dateTime: LocalDateTime = LocalDateTime.now(),
    @ColumnInfo(name = "completed") var isComplete: Boolean = false,
    @ColumnInfo(name = "created") val createdAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "updated") val updatedAt: Long = createdAt,
    @ColumnInfo(name = "notification") var notification: Boolean = false,
)