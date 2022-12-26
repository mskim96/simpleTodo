package com.msk.simpletodo.data.datasource.auth

import com.msk.simpletodo.data.model.auth.UserEntity
import kotlinx.coroutines.flow.Flow

interface AuthDatasource {
    // crud method
    suspend fun createAccount(user: UserEntity)

    suspend fun getUserByEmail(email: String)
}