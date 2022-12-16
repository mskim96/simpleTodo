package com.msk.simpletodo.data.datasource.todo

import com.msk.simpletodo.data.model.todo.CategoryWithTodo
import com.msk.simpletodo.data.model.todo.TodoEntity
import kotlinx.coroutines.flow.Flow

interface TodoDatasource {
    suspend fun createTodo(todo: TodoEntity)

    suspend fun deleteTodo(todo: TodoEntity)

    suspend fun checkTodo(todo: TodoEntity)

    // todo
    fun getTodoByCategoryId(id: Long): Flow<CategoryWithTodo>

    // Todo With Category
    fun getCategoryWithTodo(): Flow<List<CategoryWithTodo>>
}