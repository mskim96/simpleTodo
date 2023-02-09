package com.msk.simpletodo.domain.usecase.todo

import com.msk.simpletodo.domain.repository.TodoRepository
import javax.inject.Inject

class TaskUseCase @Inject constructor(private val todoRepository: TodoRepository) {
    fun getTaskByQuery(query: String) = todoRepository.getTaskByQuery(query)

    fun getTaskByRecent() = todoRepository.getTaskByRecent()
}