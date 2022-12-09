package com.msk.simpletodo.data.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.msk.simpletodo.data.model.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM user_information_table")
    fun getAllUser(): List<UserEntity>

    @Insert
    suspend fun createAccount(user: UserEntity): Long
}