package com.msk.simpletodo.domain.repository

import com.msk.simpletodo.data.model.todo.TodoEntity

interface TodoRepository {
    suspend fun createTodo(
        title: String,
        description: String,
        date: String,
        time: String,
        category: Int
    )

    suspend fun deleteTodo(todo: TodoEntity)

    suspend fun checkTodo(todo: TodoEntity)
}