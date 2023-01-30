package com.msk.simpletodo.data.datasource.todo

import com.msk.simpletodo.data.model.todo.TodoEntity

interface TodoDatasource {
    suspend fun createTodo(todo: TodoEntity)

    suspend fun deleteTodo(todo: TodoEntity)

    suspend fun editTodo(todo: TodoEntity)

    suspend fun checkTodo(todo: TodoEntity)

    suspend fun getTaskByDate(date: String): List<TodoEntity>
}