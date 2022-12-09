package com.msk.simpletodo.domain.repository

import com.msk.simpletodo.data.model.todo.TodoCategoryWithTodo
import com.msk.simpletodo.data.model.todo.TodoEntity
import com.msk.simpletodo.presentation.view.base.TodoState
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    suspend fun createTodo(todo: TodoEntity): Long

    fun getCategoryWithTodo(): Flow<TodoState<List<TodoCategoryWithTodo>>>
}