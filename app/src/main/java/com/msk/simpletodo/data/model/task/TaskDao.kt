package com.msk.simpletodo.data.model.task

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

/**
 * Data Access Object for the tasks table
 */
@Dao
interface TaskDao {

    /**
     * Insert task in the database. If the task already exists, replace it.
     *
     * @param task the task to be insert
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)

    @Update
    suspend fun checkTodo(taskEntity: TaskEntity)

    @Update
    suspend fun editTodo(taskEntity: TaskEntity): Int

    @Delete
    suspend fun deleteTodo(taskEntity: TaskEntity): Int

    @Query("SELECT * FROM tasks")
    suspend fun getTasks(): List<TaskEntity>

    @Query("SELECT * FROM tasks WHERE tid = :taskId")
    suspend fun getTaskById(taskId: Long): TaskEntity?

    @Query("SELECT * FROM tasks WHERE date Like :date")
    suspend fun getTaskByDate(date: LocalDateTime): List<TaskEntity>

    @Query("SELECT * FROM tasks WHERE title LIKE :query")
    fun getTaskByQuery(query: String): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks ORDER BY updated DESC LIMIT 5")
    fun getTaskByRecent(): Flow<List<TaskEntity>>
}