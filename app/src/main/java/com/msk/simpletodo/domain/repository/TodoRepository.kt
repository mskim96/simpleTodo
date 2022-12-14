package com.msk.simpletodo.domain.repository

import com.msk.simpletodo.data.model.todo.TodoCategoryWithTodo
import com.msk.simpletodo.data.model.todo.TodoEntity
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    suspend fun createTodo(content: String, categoryType: Long)

    suspend fun deleteTodo(todo: TodoEntity)

    fun getTodoWithCategoryId(id: Long): Flow<TodoCategoryWithTodo>

    fun getCategoryWithTodo(): Flow<List<TodoCategoryWithTodo>>
}