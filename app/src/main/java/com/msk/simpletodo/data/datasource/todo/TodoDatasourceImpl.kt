package com.msk.simpletodo.data.datasource.todo

import com.msk.simpletodo.data.model.todo.TodoDao
import com.msk.simpletodo.data.model.todo.TodoEntity
import javax.inject.Inject

class TodoDatasourceImpl @Inject constructor(
    private val todoDao: TodoDao,
) : TodoDatasource {
    override suspend fun createTodo(todo: TodoEntity) {
        return todoDao.createToDo(todo)
    }

    override suspend fun deleteTodo(todo: TodoEntity) {
        return todoDao.deleteTodo(todo)
    }

    override suspend fun editTodo(todo: TodoEntity) {
        return todoDao.editTodo(todo)
    }

    override suspend fun checkTodo(todo: TodoEntity) {
        return todoDao.checkTodo(todo)
    }

    override suspend fun getTaskByDate(date: String): List<TodoEntity> {
        return todoDao.getTaskByDate("%${date}%")
    }
}