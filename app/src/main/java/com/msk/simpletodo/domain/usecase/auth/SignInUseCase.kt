package com.msk.simpletodo.domain.usecase.auth

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.msk.simpletodo.domain.repository.AuthRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend fun execute(email: String, password: String): Task<AuthResult> {
        return repository.signIn(email, password)
    }
}