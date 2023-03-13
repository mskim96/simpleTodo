package com.msk.simpletodo.data.datasource.auth

import com.msk.simpletodo.data.model.auth.UserDao
import com.msk.simpletodo.data.model.auth.UserLocal
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthDatasourceImpl @Inject constructor(private val userDao: UserDao) : AuthDatasource {
    override suspend fun createAccount(user: UserLocal) {
        userDao.createAccount(user)
    }

    override suspend fun updateUser(user: UserLocal) {
        userDao.updateUser(user)
    }

    override suspend fun getUserByEmail(id: String): UserLocal? =
        userDao.getUserById(id)
}