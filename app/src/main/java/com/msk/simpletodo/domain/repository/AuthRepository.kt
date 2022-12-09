package com.msk.simpletodo.domain.repository

interface AuthRepository {
    suspend fun createAccount(email: String, username: String)

}