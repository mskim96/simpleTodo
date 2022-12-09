package com.msk.simpletodo.data.repository

import android.util.Log
import com.msk.simpletodo.data.datasource.todo.TodoDatasource
import com.msk.simpletodo.data.model.todo.TodoCategoryWithTodo
import com.msk.simpletodo.data.model.todo.TodoEntity
import com.msk.simpletodo.domain.repository.TodoRepository
import com.msk.simpletodo.presentation.view.base.TodoState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(private val todoDatasource: TodoDatasource) :
    TodoRepository {
    override suspend fun createTodo(todo: TodoEntity): Long {
        return todoDatasource.createTodo(todo)
    }

    override fun getCategoryWithTodo(): Flow<TodoState<List<TodoCategoryWithTodo>>> = flow {
        todoDatasource.getCategoryWithTodo().map {
            emit(TodoState.Success(it))
        }
        return@flow
    }
}