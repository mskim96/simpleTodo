package com.msk.simpletodo.domain.usecase

import com.msk.simpletodo.data.model.todo.CategoryWithTodo
import com.msk.simpletodo.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTodoWithCategoryByIdUseCase @Inject constructor(private val repository: TodoRepository) {
    fun execute(id: Long): Flow<CategoryWithTodo> {
        return repository.getTodoWithCategoryId(id)
    }
}