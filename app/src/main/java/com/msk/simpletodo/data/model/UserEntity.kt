package com.msk.simpletodo.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_information_table")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val uid: Long = 0,

    @ColumnInfo(name = "user_email")
    val email: String,

    @ColumnInfo(name = "user_username")
    val username: String,
)
