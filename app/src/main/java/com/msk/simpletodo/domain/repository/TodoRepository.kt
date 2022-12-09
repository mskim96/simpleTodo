package com.msk.simpletodo.domain.repository

import com.msk.simpletodo.data.model.todo.TodoCategoryWithTodo
import com.msk.simpletodo.data.model.todo.TodoEntity
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    suspend fun createTodo(todo: TodoEntity): Long

    fun getCategoryWithTodo(): Flow<List<TodoCategoryWithTodo>>
}