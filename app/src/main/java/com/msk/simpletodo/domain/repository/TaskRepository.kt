package com.msk.simpletodo.domain.repository

import com.msk.simpletodo.data.Result
import com.msk.simpletodo.data.model.task.TaskEntity
import kotlinx.coroutines.flow.Flow

/**
 * Interface to the data layer
 */
interface TaskRepository {
    suspend fun insertTask(task: TaskEntity)

    suspend fun deleteTodo(todo: TaskEntity): Int

    suspend fun editTodo(todo: TaskEntity): Int

    suspend fun checkTodo(todo: TaskEntity)

    suspend fun getTasks(): Result<List<TaskEntity>>

    suspend fun getTaskById(taskId: Long): Result<TaskEntity>

    suspend fun getTaskByDate(date: String): Flow<List<TaskEntity>>

    fun getTaskByQuery(query: String): Flow<List<TaskEntity>>

    fun getTaskByRecent(): Flow<List<TaskEntity>>
}