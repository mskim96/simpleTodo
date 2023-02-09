package com.msk.simpletodo.domain.repository

import com.msk.simpletodo.data.model.todo.TodoEntity
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    suspend fun createTodo(
        title: String,
        description: String,
        date: String,
        time: String,
        category: Int,
        notification: Boolean
    ): Long

    suspend fun deleteTodo(todo: TodoEntity): Int

    suspend fun editTodo(todo: TodoEntity): Int

    suspend fun checkTodo(todo: TodoEntity)

    suspend fun getTaskByDate(date: String): Flow<List<TodoEntity>>

    fun getTaskByQuery(query: String): Flow<List<TodoEntity>>

    fun getTaskByRecent(): Flow<List<TodoEntity>>
}