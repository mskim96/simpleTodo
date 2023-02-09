package com.msk.simpletodo.data.repository

import com.msk.simpletodo.data.datasource.todo.TodoDatasource
import com.msk.simpletodo.data.model.todo.TodoEntity
import com.msk.simpletodo.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(private val todoDatasource: TodoDatasource) :
    TodoRepository {

    override suspend fun createTodo(
        title: String,
        description: String,
        date: String,
        time: String,
        category: Int,
        notification: Boolean
    ): Long {
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
            category = categoryString,
            notification = notification
        )
        return todoDatasource.createTodo(todo)
    }

    override suspend fun deleteTodo(todo: TodoEntity): Int {
        return todoDatasource.deleteTodo(todo)
    }

    override suspend fun editTodo(todo: TodoEntity): Int {
        return todoDatasource.editTodo(todo)
    }

    override suspend fun checkTodo(todo: TodoEntity) {
        return todoDatasource.checkTodo(todo)
    }

    override suspend fun getTaskByDate(date: String): Flow<List<TodoEntity>> = flow {
        val tasks = todoDatasource.getTaskByDate(date)
        val taskSort = tasks.sortedWith(compareBy { it.time })
        emit(taskSort)
    }

    override fun getTaskByQuery(query: String): Flow<List<TodoEntity>> =
        todoDatasource.getTaskByQuery(query)


    override fun getTaskByRecent(): Flow<List<TodoEntity>> {
        return todoDatasource.getTaskByRecent()
    }

}