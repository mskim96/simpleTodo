package com.msk.simpletodo.data.model.auth

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_information_table")
data class UserEntity(
    @PrimaryKey
    val uid: String,

    @ColumnInfo(name = "user_email")
    val email: String,

    @ColumnInfo(name = "user_username")
    val username: String,
)
