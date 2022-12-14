package com.msk.simpletodo.data.datasource.todo

import com.msk.simpletodo.data.model.todo.CategoryWithTodo
import com.msk.simpletodo.data.model.todo.TodoCategoryDao
import com.msk.simpletodo.data.model.todo.TodoDao
import com.msk.simpletodo.data.model.todo.TodoEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TodoDatasourceImpl @Inject constructor(
    private val todoDao: TodoDao,
    private val todoCategoryDao: TodoCategoryDao,
) : TodoDatasource {
    override suspend fun createTodo(todo: TodoEntity) {
        return todoDao.createToDo(todo)
    }

    override suspend fun deleteTodo(todo: TodoEntity) {
        return todoDao.deleteTodo(todo)
    }


    override fun getTodoByCategoryId(id: Long): Flow<CategoryWithTodo> {
        return flow {
            emit(todoCategoryDao.getTodoByCategoryId(id))
        }
    }

    override fun getCategoryWithTodo(): Flow<List<CategoryWithTodo>> {
        return flow {
            emit(todoCategoryDao.getCategoryWithTodo())
        }
    }
}