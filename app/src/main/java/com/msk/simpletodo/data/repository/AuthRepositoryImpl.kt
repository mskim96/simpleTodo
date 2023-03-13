package com.msk.simpletodo.data.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.msk.simpletodo.data.datasource.auth.AuthDatasource
import com.msk.simpletodo.data.model.auth.UserLocal
import com.msk.simpletodo.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val authDatasource: AuthDatasource
) :
    AuthRepository {
    override suspend fun createAccount(email: String, password: String): Task<AuthResult> =
        withContext(Dispatchers.IO) {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
        }

    override suspend fun signIn(email: String, password: String): Task<AuthResult> =
        withContext(Dispatchers.IO) {
            firebaseAuth.signInWithEmailAndPassword(email, password)
        }

    override suspend fun signOut() {
        firebaseAuth.signOut()
    }

    override suspend fun createAccountLocal(user: UserLocal) {
        authDatasource.createAccount(user)
    }

    override suspend fun updateUserLocal(user: UserLocal) {
        authDatasource.updateUser(user)
    }

    override suspend fun getUserById(id: String): UserLocal? {
        return authDatasource.getUserByEmail(id)
    }
}
