package com.msk.simpletodo.data.datasource

import com.msk.simpletodo.data.model.TodoDao
import com.msk.simpletodo.data.model.TodoEntity
import javax.inject.Inject

class TodoDatasourceImpl @Inject constructor(private val todoDao: TodoDao) : TodoDatasource {
    override suspend fun createTodo(todo: TodoEntity): Long {
        return todoDao.createToDo(todo)
    }

    override suspend fun getUserWithTodo(id: Long): List<TodoEntity> {
        return todoDao.getUserWithTodo(id)
    }
}