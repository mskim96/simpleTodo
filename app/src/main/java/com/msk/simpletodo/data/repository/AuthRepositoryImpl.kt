package com.msk.simpletodo.data.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.msk.simpletodo.data.datasource.auth.AuthDatasource
import com.msk.simpletodo.data.mapper.toExternalModel
import com.msk.simpletodo.data.mapper.toLocalModel
import com.msk.simpletodo.data.model.auth.User
import com.msk.simpletodo.data.model.auth.UserLocal
import com.msk.simpletodo.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDatasource: FirebaseAuth,
    private val authLocalDatasource: AuthDatasource
) :
    AuthRepository {
    /**
     * From Local Datasource.
     */
    override fun observeUserById(userId: String): Flow<User> {
        return authLocalDatasource.observeUserById(userId).map { it.toExternalModel() }
    }

    override suspend fun createUser(user: User) {
        authLocalDatasource.createAccount(user.toLocalModel())
    }

    override suspend fun updateUser(user: User): Int {
        return authLocalDatasource.updateUser(user.toLocalModel())
    }


    override suspend fun getUserById(id: String): User? {
        return authLocalDatasource.getUserByEmail(id)?.toExternalModel()
    }

    /**
     * From firebase auth service.
     */
    override suspend fun createAccount(email: String, password: String): Task<AuthResult> =
        withContext(Dispatchers.IO) {
            authRemoteDatasource.createUserWithEmailAndPassword(email, password)
        }

    override suspend fun signIn(email: String, password: String): Task<AuthResult> =
        withContext(Dispatchers.IO) {
            authRemoteDatasource.signInWithEmailAndPassword(email, password)
        }

    override suspend fun signOut() {
        authRemoteDatasource.signOut()
    }

    // TODO Delete below
    override suspend fun createAccountLocal(user: UserLocal) {
        authLocalDatasource.createAccount(user)
    }

    override suspend fun updateUserLocal(user: UserLocal) {
        authLocalDatasource.updateUser(user)
    }
}
