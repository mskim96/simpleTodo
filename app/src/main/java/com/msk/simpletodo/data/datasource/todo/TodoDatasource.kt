package com.msk.simpletodo.data.datasource.todo

import com.msk.simpletodo.data.model.todo.TodoEntity
import kotlinx.coroutines.flow.Flow

interface TodoDatasource {
    suspend fun createTodo(todo: TodoEntity): Long

    suspend fun deleteTodo(todo: TodoEntity): Int

    suspend fun editTodo(todo: TodoEntity): Int

    suspend fun checkTodo(todo: TodoEntity)

    suspend fun getTaskByDate(date: String): List<TodoEntity>

    fun getTaskByQuery(query: String): Flow<List<TodoEntity>>

    fun getTaskByRecent(): Flow<List<TodoEntity>>
}