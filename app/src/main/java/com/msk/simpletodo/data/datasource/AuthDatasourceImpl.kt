package com.msk.simpletodo.data.datasource

import com.msk.simpletodo.data.model.UserDao
import com.msk.simpletodo.data.model.UserEntity
import com.msk.simpletodo.data.model.UserWithTodo
import javax.inject.Inject

class AuthDatasourceImpl @Inject constructor(private val userDao: UserDao) : AuthDatasource {
    override suspend fun createAccount(user: UserEntity){
        userDao.createAccount(user = user)
    }
}