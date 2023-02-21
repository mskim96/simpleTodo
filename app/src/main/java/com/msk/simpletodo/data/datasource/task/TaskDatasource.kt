package com.msk.simpletodo.data.datasource.task

import com.msk.simpletodo.data.Result
import com.msk.simpletodo.data.model.task.TaskEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

/**
 * Interface of a data source as a local database.
 */
interface TaskDatasource {
    suspend fun insertTask(task: TaskEntity)

    suspend fun deleteTodo(todo: TaskEntity): Int

    suspend fun editTodo(todo: TaskEntity): Int

    suspend fun checkTodo(todo: TaskEntity)

    suspend fun getTasks(): Result<List<TaskEntity>>

    suspend fun getTaskById(taskId: Long): Result<TaskEntity>

    suspend fun getTaskByDate(date: LocalDateTime): List<TaskEntity>

    fun getTaskByQuery(query: String): Flow<List<TaskEntity>>

    fun getTaskByRecent(): Flow<List<TaskEntity>>
}