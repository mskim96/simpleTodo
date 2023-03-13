package com.msk.simpletodo.data.model.auth

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM User WHERE uid =:id")
    suspend fun getUserById(id: String): UserLocal?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createAccount(user: UserLocal)

    @Update
    suspend fun updateUser(user: UserLocal)
}