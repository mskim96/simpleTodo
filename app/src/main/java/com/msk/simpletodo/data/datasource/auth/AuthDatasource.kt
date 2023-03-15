package com.msk.simpletodo.data.datasource.auth

import com.msk.simpletodo.data.model.auth.UserLocal
import kotlinx.coroutines.flow.Flow

interface AuthDatasource {

    fun observeUserById(userId: String): Flow<UserLocal>

    suspend fun getUserById(userId: String): UserLocal?

    suspend fun createUser(user: UserLocal)

    suspend fun updateUser(user: UserLocal): Int


    // TODO : Delete below
    suspend fun createAccount(user: UserLocal)

    suspend fun getUserByEmail(id: String): UserLocal?

}