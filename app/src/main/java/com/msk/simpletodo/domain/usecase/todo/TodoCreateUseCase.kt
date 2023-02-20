package com.msk.simpletodo.domain.usecase.todo

import com.msk.simpletodo.domain.repository.TaskRepository
import javax.inject.Inject

class TodoCreateUseCase @Inject constructor(private val repository: TaskRepository) {
    suspend fun execute(
        content: String,
        description: String,
        date: String,
        time: String,
        category: Int,
        notification: Boolean
    ) = repository.getTaskByRecent()
}