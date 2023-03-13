package com.msk.simpletodo.domain.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.msk.simpletodo.data.model.auth.UserLocal

interface AuthRepository {
    suspend fun createAccount(email: String, password: String): Task<AuthResult>
    suspend fun signIn(email: String, password: String): Task<AuthResult>
    suspend fun signOut()

    suspend fun createAccountLocal(user: UserLocal)

    suspend fun updateUserLocal(user: UserLocal)

    suspend fun getUserById(id: String): UserLocal?
}