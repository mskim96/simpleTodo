package com.msk.simpletodo.data.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.msk.simpletodo.data.model.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM user_information")
    fun getAllUser(): List<UserEntity>

    @Insert
    fun createAccount(user: UserEntity): Long
}