package com.msk.simpletodo.data.datasource

import com.msk.simpletodo.data.model.TodoEntity

interface TodoDatasource {
    suspend fun createTodo(todo: TodoEntity): Long

    suspend fun getUserWithTodo(id: Long): List<TodoEntity>
}