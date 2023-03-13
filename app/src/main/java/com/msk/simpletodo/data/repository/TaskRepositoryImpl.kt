package com.msk.simpletodo.data.repository

import com.msk.simpletodo.data.Result
import com.msk.simpletodo.data.datasource.task.TaskDatasource
import com.msk.simpletodo.data.model.task.TaskEntity
import com.msk.simpletodo.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import javax.inject.Inject

/**
 * Concrete implementation of a data layer
 */
class TaskRepositoryImpl @Inject constructor(private val taskDatasource: TaskDatasource) :
    TaskRepository {
    override suspend fun insertTask(task: TaskEntity) {
        return taskDatasource.insertTask(task)
    }

    override suspend fun deleteTodo(todo: TaskEntity): Int {
        return taskDatasource.deleteTodo(todo)
    }

    override suspend fun editTodo(todo: TaskEntity): Int {
        return taskDatasource.editTodo(todo)
    }

    override suspend fun checkTodo(todo: TaskEntity) {
        return taskDatasource.checkTodo(todo)
    }

    override suspend fun getTasks(): Result<List<TaskEntity>> {
        return taskDatasource.getTasks()
    }

    override suspend fun getTaskById(taskId: Long): Result<TaskEntity> {
        return taskDatasource.getTaskById(taskId)
    }

    override suspend fun getTaskByDate(date: String): Flow<List<TaskEntity>> = flow {
        val tasks = taskDatasource.getTaskByDate(LocalDateTime.now())
        val taskSort = tasks.sortedWith(compareBy { it.dateTime })
        emit(taskSort)
    }

    override fun getTaskByQuery(query: String): Flow<List<TaskEntity>> =
        taskDatasource.getTaskByQuery(query)


    override fun getTaskByRecent(): Flow<List<TaskEntity>> {
        return taskDatasource.getTaskByRecent()
    }

}