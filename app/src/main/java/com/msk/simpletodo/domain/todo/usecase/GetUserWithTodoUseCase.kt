package com.msk.simpletodo.domain.todo.usecase

import com.msk.simpletodo.data.model.TodoEntity
import com.msk.simpletodo.domain.todo.repository.TodoRepository
import javax.inject.Inject

class GetUserWithTodoUseCase @Inject constructor(private val repository: TodoRepository) {
    suspend fun execute(id: Long): List<TodoEntity> {
        return repository.getUserWithTodo(id)
    }
}