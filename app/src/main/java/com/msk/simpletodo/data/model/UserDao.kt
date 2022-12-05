package com.msk.simpletodo.data.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.msk.simpletodo.data.model.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM user_information")
    fun getAllUser(): List<UserEntity>

    @Query("SELECT * FROM user_information WHERE user_email =:email")
    fun getUserByEmail(email: String): UserEntity

    @Insert
    suspend fun createAccount(user: UserEntity): Long
}