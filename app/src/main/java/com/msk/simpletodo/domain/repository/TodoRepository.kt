package com.msk.simpletodo.domain.repository

import com.msk.simpletodo.data.model.todo.CategoryWithTodo
import com.msk.simpletodo.data.model.todo.TodoEntity
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    suspend fun createTodo(content: String, categoryType: Long)

    suspend fun deleteTodo(todo: TodoEntity)

    fun getTodoWithCategoryId(id: Long): Flow<CategoryWithTodo>

    fun getCategoryWithTodo(): Flow<List<CategoryWithTodo>>
}