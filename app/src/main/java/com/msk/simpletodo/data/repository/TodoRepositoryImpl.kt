package com.msk.simpletodo.data.repository

import com.msk.simpletodo.data.datasource.todo.TodoDatasource
import com.msk.simpletodo.data.model.todo.CategoryWithTodo
import com.msk.simpletodo.data.model.todo.TodoEntity
import com.msk.simpletodo.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(private val todoDatasource: TodoDatasource) :
    TodoRepository {

    override suspend fun createTodo(content: String, categoryType: Long) {
        return todoDatasource.createTodo(TodoEntity(content = content, fkCategory = categoryType))
    }

    override suspend fun deleteTodo(todo: TodoEntity) {
        return todoDatasource.deleteTodo(todo)
    }

    override fun getTodoWithCategoryId(id: Long): Flow<CategoryWithTodo> {
        return todoDatasource.getTodoByCategoryId(id)
    }

    override fun getCategoryWithTodo(): Flow<List<CategoryWithTodo>> {
        return todoDatasource.getCategoryWithTodo()
    }
}