package com.msk.simpletodo.data.datasource.auth

import com.msk.simpletodo.data.model.auth.UserEntity

interface AuthDatasource {
    // crud method
    suspend fun createAccount(user: UserEntity)
}