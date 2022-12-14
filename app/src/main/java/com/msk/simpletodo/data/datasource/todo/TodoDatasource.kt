package com.msk.simpletodo.data.datasource.todo

import com.msk.simpletodo.data.model.todo.TodoCategoryWithTodo
import com.msk.simpletodo.data.model.todo.TodoEntity
import kotlinx.coroutines.flow.Flow

interface TodoDatasource {
    suspend fun createTodo(todo: TodoEntity)

    suspend fun deleteTodo(todo: TodoEntity)

    // todo
    fun getTodoByCategoryId(id: Long): Flow<TodoCategoryWithTodo>

    // Todo With Category
    fun getCategoryWithTodo(): Flow<List<TodoCategoryWithTodo>>
}