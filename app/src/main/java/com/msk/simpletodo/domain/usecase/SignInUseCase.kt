package com.msk.simpletodo.domain.usecase

import com.msk.simpletodo.data.model.UserEntity
import com.msk.simpletodo.domain.repository.AuthRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend fun execute(email: String): UserEntity {
        return repository.getUserByEmail(email)
    }
}