package com.msk.simpletodo.data.model.auth

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class UserLocal(
    @PrimaryKey val uid: String,
    @ColumnInfo(name = "email") val email: String = "",
    @ColumnInfo(name = "username") val username: String = "",
    @ColumnInfo(name = "profileImg") val profileImage: String = "",
    @ColumnInfo(name = "bio") val vio: String = "",
    @ColumnInfo(name = "voiceBio") val voiceVio: String = ""
)
