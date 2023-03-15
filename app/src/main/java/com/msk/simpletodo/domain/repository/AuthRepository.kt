package com.msk.simpletodo.domain.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.msk.simpletodo.data.model.auth.User
import com.msk.simpletodo.data.model.auth.UserLocal
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    /**
     * From Local Datasource.
     */
    fun observeUserById(userId: String): Flow<User>

    suspend fun getUserById(userId: String): User?

    suspend fun createUser(user: User)

    suspend fun updateUser(user: User): Int


    /**
     * From firebase auth service.
     */

    suspend fun createAccount(email: String, password: String): Task<AuthResult>
    suspend fun signIn(email: String, password: String): Task<AuthResult>
    suspend fun signOut()

    suspend fun createAccountLocal(user: UserLocal)

    suspend fun updateUserLocal(user: UserLocal)

}