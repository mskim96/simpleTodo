package com.msk.simpletodo.domain.usecase.todo

import com.msk.simpletodo.domain.repository.TodoRepository
import javax.inject.Inject

class TodoCreateUseCase @Inject constructor(private val repository: TodoRepository) {
    suspend fun execute(
        content: String,
        description: String,
        date: String,
        time: String,
        category: Int
    ) = repository.createTodo(content, description, date, time, category)
}