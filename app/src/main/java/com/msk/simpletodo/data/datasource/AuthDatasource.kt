package com.msk.simpletodo.data.datasource

import com.msk.simpletodo.data.model.UserEntity

interface AuthDatasource {
    suspend fun createAccount(user: UserEntity): Long
}