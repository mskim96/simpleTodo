package com.msk.simpletodo.domain.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

interface AuthRepository {
    suspend fun createAccount(email: String, password: String, username: String)

    suspend fun signIn(email: String, password: String): Task<AuthResult>
}