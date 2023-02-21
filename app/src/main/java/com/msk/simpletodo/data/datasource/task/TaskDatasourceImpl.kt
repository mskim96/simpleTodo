package com.msk.simpletodo.data.datasource.task

import com.msk.simpletodo.data.Result
import com.msk.simpletodo.data.model.task.TaskDao
import com.msk.simpletodo.data.model.task.TaskEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject

/**
 * implements of a data source as a local database.
 */
class TaskDatasourceImpl @Inject constructor(
    private val taskDao: TaskDao,
) : TaskDatasource {

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun insertTask(task: TaskEntity) {
        return taskDao.insertTask(task)
    }

    override suspend fun deleteTodo(todo: TaskEntity): Int {
        return taskDao.deleteTodo(todo)
    }

    override suspend fun editTodo(todo: TaskEntity): Int {
        return taskDao.editTodo(todo)
    }

    override suspend fun checkTodo(todo: TaskEntity) {
        return taskDao.checkTodo(todo)
    }

    override suspend fun getTasks(): Result<List<TaskEntity>> = withContext(ioDispatcher) {
        return@withContext try {
            Result.Success(taskDao.getTasks())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getTaskById(taskId: Long): Result<TaskEntity> = withContext(ioDispatcher) {
        try {
            val task = taskDao.getTaskById(taskId)
            if (task != null) {
                return@withContext Result.Success(task)
            } else {
                return@withContext Result.Error(Exception("Task not found"))
            }
        } catch (e: Exception) {
            return@withContext Result.Error(e)
        }
    }

    override suspend fun getTaskByDate(date: LocalDateTime): List<TaskEntity> {
        val testLocalDate = LocalDateTime.now()
        return taskDao.getTaskByDate(testLocalDate)
    }

    override fun getTaskByQuery(query: String): Flow<List<TaskEntity>> =
        taskDao.getTaskByQuery("%${query}%")


    override fun getTaskByRecent(): Flow<List<TaskEntity>> =
        taskDao.getTaskByRecent()
}