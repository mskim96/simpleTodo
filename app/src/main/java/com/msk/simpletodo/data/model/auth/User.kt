package com.msk.simpletodo.data.model.auth

import java.util.UUID

/**
 * Immutable model class for USer
 *
 * @param userId userId of the user
 * @param email email of the user
 * @param username username of the user
 * @param profileImage url of user's profile image
 * @param bio bio of the user
 * @param voiceBio url of user's voice file
 */
data class User(
    val userId: String = UUID.randomUUID().toString(),
    val email: String = "",
    val username: String = "",
    val profileImage: String = "",
    val bio: String = "",
    val voiceBio: String = ""
)