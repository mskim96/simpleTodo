package com.msk.simpletodo.data.repository

import com.msk.simpletodo.data.datasource.todo.TodoDatasource
import com.msk.simpletodo.data.model.todo.TodoEntity
import com.msk.simpletodo.domain.repository.TodoRepository
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(private val todoDatasource: TodoDatasource) :
    TodoRepository {

    override suspend fun createTodo(
        title: String,
        description: String,
        date: String,
        time: String,
        category: Int
    ) {
        val categoryString = when (category) {
            0 -> "Personal"
            1 -> "Work"
            2 -> "Study"
            3 -> "Others"
            else -> throw IllegalArgumentException("Can't have category")
        }
        val todo = TodoEntity(
            title = title,
            description = description,
            date = date,
            time = time,
            category = categoryString
        )
        return todoDatasource.createTodo(todo)
    }

    override suspend fun deleteTodo(todo: TodoEntity) {
        return todoDatasource.deleteTodo(todo)
    }

    override suspend fun checkTodo(todo: TodoEntity) {
        return todoDatasource.checkTodo(todo)
    }
}