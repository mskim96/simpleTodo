package com.msk.simpletodo.domain.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.msk.simpletodo.data.model.auth.UserEntity
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun getUserByEmail(email: String): UserEntity
    suspend fun saveUserInLocal(uid: String, email: String, username: String)

    suspend fun createAccount(email: String, password: String, username: String)
    suspend fun signIn(email: String, password: String): Task<AuthResult>
    suspend fun signOut()
}