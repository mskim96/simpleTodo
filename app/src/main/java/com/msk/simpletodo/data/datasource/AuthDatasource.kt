package com.msk.simpletodo.data.datasource

import com.msk.simpletodo.data.model.UserEntity

interface AuthDatasource {
    // crud method
    suspend fun createAccount(user: UserEntity): Long

    // query Method
    suspend fun getUserByEmail(email: String): UserEntity
}