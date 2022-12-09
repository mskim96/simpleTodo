package com.msk.simpletodo.domain.auth.repository

import com.msk.simpletodo.data.model.UserEntity

interface AuthRepository {
    suspend fun createAccount(email: String, username: String)

}