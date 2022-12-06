package com.msk.simpletodo.domain.auth.repository

import com.msk.simpletodo.data.model.UserEntity

interface AuthRepository {
    suspend fun createAccount(email: String, password: String, username: String): Long

    suspend fun getUserByEmail(email: String): UserEntity
}