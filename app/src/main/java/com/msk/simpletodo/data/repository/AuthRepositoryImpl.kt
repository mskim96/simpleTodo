package com.msk.simpletodo.data.repository

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.msk.simpletodo.data.datasource.auth.AuthDatasource
import com.msk.simpletodo.data.model.auth.UserEntity
import com.msk.simpletodo.domain.repository.AuthRepository
import com.msk.simpletodo.presentation.viewModel.auth.toSuspendable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val datasource: AuthDatasource,
    private val firebaseAuth: FirebaseAuth
) :
    AuthRepository {
    override suspend fun createAccount(email: String, password: String, username: String) {
        withContext(Dispatchers.IO) {
            runCatching {
                firebaseAuth.createUserWithEmailAndPassword(email, password).toSuspendable()
            }.onSuccess {
                val user = firebaseAuth.currentUser?.let { UserEntity(it.uid, email, username) }
                val usernameReq: UserProfileChangeRequest =
                    UserProfileChangeRequest.Builder().setDisplayName(username).build()
                firebaseAuth.currentUser?.updateProfile(usernameReq)?.addOnCompleteListener { it }
                datasource.createAccount(user!!)
            }
        }
    }

    override suspend fun signIn(email: String, password: String): Task<AuthResult> =
        withContext(Dispatchers.IO) {
            firebaseAuth.signInWithEmailAndPassword(email, password)
        }
}
