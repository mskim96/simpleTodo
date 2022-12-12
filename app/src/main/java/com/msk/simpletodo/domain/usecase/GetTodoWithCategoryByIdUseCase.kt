package com.msk.simpletodo.domain.usecase

import com.msk.simpletodo.data.model.todo.TodoCategoryWithTodo
import com.msk.simpletodo.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTodoWithCategoryByIdUseCase @Inject constructor(private val repository: TodoRepository) {
    fun execute(id: Long): Flow<TodoCategoryWithTodo> {
        return repository.getTodoWithCategoryId(id)
    }
}