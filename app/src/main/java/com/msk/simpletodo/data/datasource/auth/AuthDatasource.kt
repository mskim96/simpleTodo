package com.msk.simpletodo.data.datasource.auth

import com.msk.simpletodo.data.model.auth.UserLocal

interface AuthDatasource {
    // crud method
    suspend fun createAccount(user: UserLocal)

    suspend fun updateUser(user: UserLocal)

    suspend fun getUserByEmail(id: String): UserLocal?

}