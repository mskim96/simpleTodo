package com.msk.simpletodo.data.model.auth

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createAccount(user: UserLocal)

    /**
     * Observe a user information
     *
     * @param userId id of the user
     */
    @Query("SELECT * FROM User WHERE userId =:userId")
    fun observeUserById(userId: String): Flow<UserLocal>

    /**
     * Get user information by id
     *
     * @param userId if of the user
     */
    @Query("SELECT * FROM User WHERE userId =:userId")
    suspend fun getUserById(userId: String): UserLocal?

    /**
     * Create user in the database.
     *
     * @param user the user to be create.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createUser(user: UserLocal)

    /**
     * Update user information
     *
     * @param user the user to be updated.
     * @return return 1 if update success.
     */
    @Update
    suspend fun updateUser(user: UserLocal): Int
}