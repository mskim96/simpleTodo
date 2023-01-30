package com.msk.simpletodo.domain.repository

import com.msk.simpletodo.data.model.todo.TodoEntity
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    suspend fun createTodo(
        title: String,
        description: String,
        date: String,
        time: String,
        category: Int
    )

    suspend fun deleteTodo(todo: TodoEntity)

    suspend fun editTodo(todo: TodoEntity)

    suspend fun checkTodo(todo: TodoEntity)

    suspend fun getTaskByDate(date: String): Flow<List<TodoEntity>>
}