package com.msk.simpletodo.data.model.auth

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM user_information_table")
    fun getAllUser(): List<UserEntity>

    @Insert
    suspend fun createAccount(user: UserEntity): Long
}