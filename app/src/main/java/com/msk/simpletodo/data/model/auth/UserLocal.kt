package com.msk.simpletodo.data.model.auth

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Local user entity.
 *
 * @param uid userId
 * @param email user email
 * @param username username
 * @param profileImage url of user profile image
 * @param bio user bio
 * @param voiceBio url of user voice bio
 */
@Entity(tableName = "User")
data class UserLocal(
    @PrimaryKey @ColumnInfo(name = "userId") val uid: String,
    @ColumnInfo(name = "email") val email: String = "",
    @ColumnInfo(name = "username") val username: String = "",
    @ColumnInfo(name = "profileImg") val profileImage: String = "",
    @ColumnInfo(name = "bio") val bio: String = "",
    @ColumnInfo(name = "voiceBio") val voiceBio: String = ""
)
