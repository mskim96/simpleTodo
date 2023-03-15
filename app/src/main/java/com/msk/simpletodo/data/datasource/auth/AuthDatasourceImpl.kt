package com.msk.simpletodo.data.datasource.auth

import com.msk.simpletodo.data.model.auth.UserDao
import com.msk.simpletodo.data.model.auth.UserLocal
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthDatasourceImpl @Inject constructor(private val userDao: UserDao) : AuthDatasource {

    override fun observeUserById(userId: String): Flow<UserLocal> {
        return userDao.observeUserById(userId)
    }

    override suspend fun getUserById(userId: String): UserLocal? {
        return userDao.getUserById(userId)
    }

    override suspend fun createUser(user: UserLocal) {
        return userDao.createUser(user)
    }

    override suspend fun updateUser(user: UserLocal): Int {
        return userDao.updateUser(user)
    }

    // TODO Delete below
    override suspend fun createAccount(user: UserLocal) {
        userDao.createAccount(user)
    }

    override suspend fun getUserByEmail(id: String): UserLocal? =
        userDao.getUserById(id)
}