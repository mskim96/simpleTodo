package com.msk.simpletodo.domain.usecase

import com.msk.simpletodo.domain.repository.AuthRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend fun execute(email: String, password: String, username: String): Long {
        return repository.createAccount(email, password, username)
    }
}