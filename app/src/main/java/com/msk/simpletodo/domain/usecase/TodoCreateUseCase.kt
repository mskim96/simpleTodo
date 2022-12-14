package com.msk.simpletodo.domain.usecase

import com.msk.simpletodo.domain.repository.TodoRepository
import javax.inject.Inject

class TodoCreateUseCase @Inject constructor(private val repository: TodoRepository) {
    suspend fun execute(content: String, categoryType: Long) {
        return repository.createTodo(content, categoryType)
    }
}