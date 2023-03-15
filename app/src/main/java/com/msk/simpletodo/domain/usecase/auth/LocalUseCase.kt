package com.msk.simpletodo.domain.usecase.auth

import com.msk.simpletodo.data.mapper.toLocalModel
import com.msk.simpletodo.data.model.auth.UserLocal
import com.msk.simpletodo.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalUseCase @Inject constructor(private val authRepository: AuthRepository) {

    suspend fun createAccount(user: UserLocal) {
        authRepository.createAccountLocal(user)
    }

    suspend fun updateUser(user: UserLocal) {
        authRepository.updateUserLocal(user)
    }

    suspend fun getUserById(id: String): UserLocal? {
        return authRepository.getUserById(id)?.toLocalModel()
    }
}