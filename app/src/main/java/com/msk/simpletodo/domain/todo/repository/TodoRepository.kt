package com.msk.simpletodo.domain.todo.repository

import com.msk.simpletodo.data.model.TodoEntity

interface TodoRepository {
    suspend fun createTodo(todo: TodoEntity): Long

    suspend fun getUserWithTodo(id: Long): List<TodoEntity>
}