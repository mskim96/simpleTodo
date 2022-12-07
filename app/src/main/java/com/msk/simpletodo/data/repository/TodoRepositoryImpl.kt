package com.msk.simpletodo.data.repository

import com.msk.simpletodo.data.datasource.TodoDatasource
import com.msk.simpletodo.data.model.TodoEntity
import com.msk.simpletodo.domain.todo.repository.TodoRepository
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(private val todoDatasource: TodoDatasource) :
    TodoRepository {
    override suspend fun createTodo(todo: TodoEntity): Long {
        return todoDatasource.createTodo(todo)
    }

    override suspend fun getUserWithTodo(id: Long): List<TodoEntity> {
        return todoDatasource.getUserWithTodo(id)
    }
}