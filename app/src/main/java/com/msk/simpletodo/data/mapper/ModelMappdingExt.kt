package com.msk.simpletodo.data.mapper

import com.msk.simpletodo.data.model.auth.User
import com.msk.simpletodo.data.model.auth.UserLocal

fun User.toLocalModel() = UserLocal(
    uid = userId,
    email = email,
    username = username,
    profileImage = profileImage,
    bio = bio,
    voiceBio = voiceBio
)

fun UserLocal.toExternalModel() = User(
    userId = uid,
    email = email,
    username = username,
    profileImage = profileImage,
    bio = bio,
    voiceBio = voiceBio
)