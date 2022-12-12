package com.msk.simpletodo.domain.repository

import com.msk.simpletodo.data.model.todo.TodoCategoryWithTodo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    suspend fun createTodo(content: String, categoryType: Long)

    fun getTodoWithCategoryId(id: Long): Flow<TodoCategoryWithTodo>

    fun getCategoryWithTodo(): Flow<List<TodoCategoryWithTodo>>
}